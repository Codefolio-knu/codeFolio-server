package com.jandy.codeFolio.present.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUpdateRequest {
    private String content;
    private Long userId;
}
