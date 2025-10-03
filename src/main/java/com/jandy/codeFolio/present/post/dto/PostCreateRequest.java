package com.jandy.codeFolio.present.post.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String content;
    private List<Long> skillIds;
    private LocalDate endDate;
}
