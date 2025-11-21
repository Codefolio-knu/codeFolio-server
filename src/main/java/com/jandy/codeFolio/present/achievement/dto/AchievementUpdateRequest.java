package com.jandy.codeFolio.present.achievement.dto;

import com.jandy.codeFolio.domain.achievement.AchievementType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class AchievementUpdateRequest {

    @NotNull
    private Long userId;

    @NotNull
    private AchievementType type;

    // Common fields
    @NotNull
    private String title;
    private String briefDescription;
    private String description;

    // Project fields
    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private Boolean isAwarded;
    private List<String> skills;

    // Contest fields
    private String host;
    private String award;
}
