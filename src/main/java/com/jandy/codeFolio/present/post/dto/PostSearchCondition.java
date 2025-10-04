package com.jandy.codeFolio.present.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostSearchCondition {
    private Integer capacity;
    private List<Long> skillIds;

    public enum SortType {
        DEADLINE_ASC,
        CREATED_DESC
    }

    private SortType sortType;
}