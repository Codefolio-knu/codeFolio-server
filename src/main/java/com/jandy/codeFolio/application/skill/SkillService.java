package com.jandy.codeFolio.application.skill;

import com.jandy.codeFolio.application.oauth.GithubService;
import com.jandy.codeFolio.domain.project.Project;
import com.jandy.codeFolio.domain.project.ProjectRepository;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.skill.SkillRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.oauth.dto.GithubRepoResponse;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final GithubService githubService;
    private final ProjectRepository projectRepository;

    @Transactional
    public void syncSkills(Long userId) {
        long totalStart = System.currentTimeMillis();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        String accessToken = user.getAccessTokenEncrypted();

        long reposApiStart = System.currentTimeMillis();
        List<GithubRepoResponse> repos = githubService.getRepositories(accessToken);
        long reposApiTime = System.currentTimeMillis() - reposApiStart;

        List<GithubRepoResponse> nonForkRepos = repos.stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());

        // 병렬 페이즈: Virtual Thread로 getLanguages() 동시 호출
        long parallelApiStart = System.currentTimeMillis();
        Map<String, Map<String, Long>> repoLanguageMap;
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<CompletableFuture<Map.Entry<String, Map<String, Long>>>> futures = nonForkRepos.stream()
                    .map(repo -> CompletableFuture.supplyAsync(
                            () -> Map.entry(repo.getName(), githubService.getLanguages(repo.getLanguagesUrl(), accessToken)),
                            executor
                    ))
                    .collect(Collectors.toList());

            repoLanguageMap = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
        }
        long parallelApiTime = System.currentTimeMillis() - parallelApiStart;

        // 순차 페이즈: 배치 조회 후 DB 작업 (@Transactional 컨텍스트 유지)
        long totalDbTime = 0;
        int dbQueryCount = 1; // findById

        // 프로젝트 배치 조회 (98회 → 1회)
        Set<String> repoTitles = nonForkRepos.stream()
                .map(GithubRepoResponse::getName)
                .collect(Collectors.toSet());
        long dbStart = System.currentTimeMillis();
        Map<String, Project> projectMap = projectRepository.findByUserAndTitleIn(user, repoTitles)
                .stream().collect(Collectors.toMap(Project::getTitle, p -> p));
        totalDbTime += System.currentTimeMillis() - dbStart;
        dbQueryCount++;

        // 언어명 배치 조회 (~207회 → 1회)
        Set<String> allLanguageNames = repoLanguageMap.values().stream()
                .flatMap(m -> m.keySet().stream())
                .collect(Collectors.toSet());
        long skillFetchStart = System.currentTimeMillis();
        Map<String, Skill> skillMap = skillRepository.findByNameIn(allLanguageNames)
                .stream().collect(Collectors.toMap(Skill::getName, s -> s));
        totalDbTime += System.currentTimeMillis() - skillFetchStart;
        dbQueryCount++;

        // 신규 스킬만 저장
        List<Skill> newSkills = allLanguageNames.stream()
                .filter(name -> !skillMap.containsKey(name))
                .map(name -> Skill.builder().name(name).build())
                .collect(Collectors.toList());
        if (!newSkills.isEmpty()) {
            long newSkillSaveStart = System.currentTimeMillis();
            skillRepository.saveAll(newSkills).forEach(s -> skillMap.put(s.getName(), s));
            totalDbTime += System.currentTimeMillis() - newSkillSaveStart;
            dbQueryCount++;
        }

        // 루프: DB 조회 없이 메모리 맵에서 처리
        Set<Skill> totalUserSkills = new HashSet<>();
        List<Project> projectsToSave = new ArrayList<>();

        for (GithubRepoResponse repo : nonForkRepos) {
            Project project = projectMap.getOrDefault(repo.getName(),
                    Project.builder().user(user).title(repo.getName()).build());

            project.update(repo.getDescription(), repo.getHtmlUrl());
            project.getSkills().clear();

            Map<String, Long> languages = repoLanguageMap.getOrDefault(repo.getName(), Map.of());
            for (String langName : languages.keySet()) {
                Skill skill = skillMap.get(langName);
                project.getSkills().add(skill);
                totalUserSkills.add(skill);
            }
            projectsToSave.add(project);
        }

        // 프로젝트 일괄 저장 (98회 → 1회 saveAll 호출)
        long saveStart = System.currentTimeMillis();
        projectRepository.saveAll(projectsToSave);
        totalDbTime += System.currentTimeMillis() - saveStart;
        dbQueryCount++;

        long userSaveStart = System.currentTimeMillis();
        user.getSkills().clear();
        user.getSkills().addAll(totalUserSkills);
        userRepository.save(user);
        totalDbTime += System.currentTimeMillis() - userSaveStart;
        dbQueryCount++;

        long totalGithubApiTime = reposApiTime + parallelApiTime;
        long totalTime = System.currentTimeMillis() - totalStart;

        log.info("=== [AFTER-2] syncSkills 성능 측정 (userId={}) ===", userId);
        log.info("전체 repo 수: {}, 처리된 repo 수(fork 제외): {}", repos.size(), nonForkRepos.size());
        log.info("GitHub API 호출 횟수: {}회 | 병렬 처리 시간: {}ms (repos 조회: {}ms + 언어 병렬: {}ms)",
                1 + nonForkRepos.size(), totalGithubApiTime, reposApiTime, parallelApiTime);
        log.info("DB 쿼리 횟수: {}회 | 누적 시간: {}ms", dbQueryCount, totalDbTime);
        log.info("총 실행 시간: {}ms (GitHub API {}ms + DB {}ms + 기타 {}ms)",
                totalTime, totalGithubApiTime, totalDbTime,
                totalTime - totalGithubApiTime - totalDbTime);
        log.info("================================================");
    }

    @Transactional(readOnly = true)
    public List<SkillResponse> getSkillsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        return user.getSkills().stream()
                .map(SkillResponse::from)
                .collect(Collectors.toList());
    }
}
