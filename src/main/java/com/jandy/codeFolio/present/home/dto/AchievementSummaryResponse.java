package com.jandy.codeFolio.present.home.dto;

import com.jandy.codeFolio.domain.achievement.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementSummaryResponse {
    private String title;
    private AchievementType type;
    private String award;
    private String link;
}
