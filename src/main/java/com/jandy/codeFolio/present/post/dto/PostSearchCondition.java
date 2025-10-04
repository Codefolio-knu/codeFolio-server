package com.jandy.codeFolio.present.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostSearchCondition {
    private Integer capacity;
    private List<Long> skillIds;
}