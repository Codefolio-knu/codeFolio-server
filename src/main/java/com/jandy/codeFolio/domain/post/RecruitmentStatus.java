package com.jandy.codeFolio.domain.post;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 모집 상태 (RECRUITING: 모집중, COMPLETED: 모집완료)")
public enum RecruitmentStatus {
    RECRUITING,
    COMPLETED
}
