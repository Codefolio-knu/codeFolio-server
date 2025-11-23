package com.jandy.codeFolio.present.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequest {
    private String title;
    private String briefDescription;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> skills;
    private Boolean isAwarded;
}
