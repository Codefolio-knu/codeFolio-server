package com.jandy.codeFolio.application.skill;

import com.jandy.codeFolio.application.oauth.GithubService;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.skill.SkillRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.oauth.dto.GithubRepoResponse;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final GithubService githubService;

    @Transactional
    public void syncSkills(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        String accessToken = user.getAccessTokenEncrypted();

        List<GithubRepoResponse> repos = githubService.getRepositories(accessToken);

        Set<String> languageNames = new HashSet<>();
        for (GithubRepoResponse repo : repos) {
            Map<String, Long> languages = githubService.getLanguages(repo.getLanguagesUrl(), accessToken);
            languageNames.addAll(languages.keySet());
        }

        List<Skill> skills = skillRepository.findByNameIn(languageNames);

        user.getSkills().clear();
        user.getSkills().addAll(skills);
        userRepository.save(user);
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
