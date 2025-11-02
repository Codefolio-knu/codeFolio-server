package com.jandy.codeFolio.present.achievement.dto;

import com.jandy.codeFolio.domain.achievement.Achievement;
import com.jandy.codeFolio.domain.achievement.AchievementType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AchievementListResponse {
    private Long id;
    private AchievementType type;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private String host;
    private String award;
    private Boolean isAwarded;

    public static AchievementListResponse from(Achievement achievement) {
        return AchievementListResponse.builder()
                .id(achievement.getId())
                .type(achievement.getType())
                .title(achievement.getTitle())
                .description(achievement.getDescription())
                .startDate(achievement.getStartDate())
                .endDate(achievement.getEndDate())
                .link(achievement.getLink())
                .host(achievement.getHost())
                .award(achievement.getAward())
                .isAwarded(achievement.getIsAwarded())
                .build();
    }
}
