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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        int totalRepos = repos.size();
        int processedRepos = 0;
        int githubApiCallCount = 1;
        int dbQueryCount = 1;

        long totalGithubApiTime = reposApiTime;
        long totalDbTime = 0;

        Set<Skill> totalUserSkills = new HashSet<>();

        for (GithubRepoResponse repo : repos) {
            if (repo.isFork()) {
                continue;
            }
            processedRepos++;

            long dbStart = System.currentTimeMillis();
            Project project = projectRepository.findByUserAndTitle(user, repo.getName())
                    .orElse(Project.builder()
                            .user(user)
                            .title(repo.getName())
                            .build());
            totalDbTime += System.currentTimeMillis() - dbStart;
            dbQueryCount++;

            project.update(repo.getDescription(), repo.getHtmlUrl());
            project.getSkills().clear();

            long langApiStart = System.currentTimeMillis();
            Map<String, Long> languages = githubService.getLanguages(repo.getLanguagesUrl(), accessToken);
            totalGithubApiTime += System.currentTimeMillis() - langApiStart;
            githubApiCallCount++;

            for (String langName : languages.keySet()) {
                long skillDbStart = System.currentTimeMillis();
                Skill skill = skillRepository.findByName(langName)
                        .orElseGet(() -> skillRepository.save(Skill.builder().name(langName).build()));
                totalDbTime += System.currentTimeMillis() - skillDbStart;
                dbQueryCount++;

                project.getSkills().add(skill);
                totalUserSkills.add(skill);
            }

            long saveStart = System.currentTimeMillis();
            projectRepository.save(project);
            totalDbTime += System.currentTimeMillis() - saveStart;
            dbQueryCount++;
        }

        long userSaveStart = System.currentTimeMillis();
        user.getSkills().clear();
        user.getSkills().addAll(totalUserSkills);
        userRepository.save(user);
        totalDbTime += System.currentTimeMillis() - userSaveStart;
        dbQueryCount++;

        long totalTime = System.currentTimeMillis() - totalStart;

        log.info("=== [BEFORE] syncSkills 성능 측정 (userId={}) ===", userId);
        log.info("전체 repo 수: {}, 처리된 repo 수(fork 제외): {}", totalRepos, processedRepos);
        log.info("GitHub API 호출 횟수: {}회 | 누적 시간: {}ms", githubApiCallCount, totalGithubApiTime);
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
