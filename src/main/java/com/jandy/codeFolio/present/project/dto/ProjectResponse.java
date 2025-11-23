package com.jandy.codeFolio.present.project.dto;

import com.jandy.codeFolio.domain.project.Project;
import com.jandy.codeFolio.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String briefDescription;
    private String description;
    private String repoUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isAwarded;
    private List<String> skills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .briefDescription(project.getBriefDescription())
                .description(project.getDescription())
                .repoUrl(project.getRepoUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .isAwarded(project.getIsAwarded())
                .skills(project.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
