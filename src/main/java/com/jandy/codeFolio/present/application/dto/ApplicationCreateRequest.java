package com.jandy.codeFolio.present.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationCreateRequest {
    private Long postId;
    private Long userId;
    private String content;
}
