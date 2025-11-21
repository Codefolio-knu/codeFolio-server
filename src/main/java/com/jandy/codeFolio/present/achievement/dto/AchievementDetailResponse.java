package com.jandy.codeFolio.present.achievement.dto;

import com.jandy.codeFolio.domain.achievement.Achievement;
import com.jandy.codeFolio.domain.achievement.AchievementType;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class AchievementDetailResponse {
    private Long id;
    private AchievementType type;
    private String title;
    private String briefDescription;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private String host;
    private String award;
    private Boolean isAwarded;
    private List<SkillResponse> skills;

    public static AchievementDetailResponse from(Achievement achievement) {
        return AchievementDetailResponse.builder()
                .id(achievement.getId())
                .type(achievement.getType())
                .title(achievement.getTitle())
                .briefDescription(achievement.getBriefDescription())
                .description(achievement.getDescription())
                .startDate(achievement.getStartDate())
                .endDate(achievement.getEndDate())
                .link(achievement.getLink())
                .host(achievement.getHost())
                .award(achievement.getAward())
                .isAwarded(achievement.getIsAwarded())
                .skills(achievement.getSkills().stream()
                        .map(SkillResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
