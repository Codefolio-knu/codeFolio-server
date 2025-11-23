package com.jandy.codeFolio.present.project.dto;

import com.jandy.codeFolio.domain.project.Project;
import com.jandy.codeFolio.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String repoUrl;
    private Boolean isAward;
    private List<String> skills;

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .briefDescription(project.getBriefDescription())
                .repoUrl(project.getRepoUrl())
                .isAward(project.getIsAwarded())
                .skills(project.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))
                .build();
    }
}
