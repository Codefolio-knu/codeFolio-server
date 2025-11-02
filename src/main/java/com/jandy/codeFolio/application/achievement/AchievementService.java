package com.jandy.codeFolio.application.achievement;

import com.jandy.codeFolio.domain.achievement.Achievement;
import com.jandy.codeFolio.domain.achievement.AchievementRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    @Transactional
    public AchievementCreateResponse createAchievement(AchievementCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        Achievement achievement = Achievement.builder()
                .user(user)
                .type(request.getType())
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .link(request.getLink())
                .host(request.getHost())
                .award(request.getAward())
                .isAwarded(request.getIsAwarded())
                .build();

        Achievement savedAchievement = achievementRepository.save(achievement);

        return AchievementCreateResponse.builder().id(savedAchievement.getId()).build();
    }

    @Transactional
    public AchievementUpdateResponse updateAchievement(Long id, AchievementUpdateRequest request) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.ACHIEVEMENT_NOT_FOUND));

        achievement.update(
                request.getType(),
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getLink(),
                request.getHost(),
                request.getAward(),
                request.getIsAwarded()
        );

        return AchievementUpdateResponse.builder().id(achievement.getId()).build();
    }
}
