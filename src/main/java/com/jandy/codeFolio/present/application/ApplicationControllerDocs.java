package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Application API", description = "게시글 지원 관련 API 명세")
public interface ApplicationControllerDocs {

    @Operation(
            summary = "게시글에 지원하기",
            description = "사용자가 특정 게시글에 지원합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "지원 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "POST_NOT_FOUND (게시글 ID가 존재하지 않음) 또는 USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "USER_ALREADY_APPLIED (이미 지원한 게시물)"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CANNOT_APPLY_TO_OWN_POST (자신의 게시물에는 지원할 수 없음) 또는 CAPACITY_FULL (모집 인원 마감)"
            )
    })
    ApplicationCreateResponse createApplication(
            @Parameter(name = "postId", description = "지원할 게시글 ID", required = true)
            @PathVariable Long postId,
            @RequestBody ApplicationCreateRequest request
    );
}
