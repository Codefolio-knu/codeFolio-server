package com.jandy.codeFolio.present.achievement.dto;

import com.jandy.codeFolio.domain.achievement.AchievementType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AchievementCreateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private AchievementType type;

    // Common fields
    @NotNull
    private String title;
    private String description;

    // Project fields
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private Boolean isAwarded;

    // Contest fields
    private String host;
    private String award;
}
