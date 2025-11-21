package com.jandy.codeFolio.present.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {
    private String name;
    private Long id;
    private String bio;
    private String major;
    private List<SkillSummaryResponse> skills;
    private List<AchievementSummaryResponse> achievements;
}
