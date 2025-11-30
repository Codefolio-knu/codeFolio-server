package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import com.jandy.codeFolio.present.application.dto.ApplicationStatusUpdateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationUpdateRequest;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Application API", description = "게시글 지원 관련 API 명세")
public interface ApplicationControllerDocs {

    @Operation(
            summary = "게시글에 지원하기",
            description = "사용자가 특정 게시글에 지원합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
    ResponseEntity<ApiResponseWrapper<ApplicationCreateResponse>> createApplication(
            @RequestBody ApplicationCreateRequest request
    );

    @Operation(
            summary = "지원서 상세 조회",
            description = "지원자가 본인의 지원서 상세 내용을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상세 내용 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "APPLICATION_NOT_FOUND (지원서 ID가 존재하지 않음) 또는 USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "NO_AUTHORITY (권한 없음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<ApplicantInfoResponse>> getApplicationDetails(
            @Parameter(name = "applicationId", description = "조회할 지원서 ID", required = true)
            @PathVariable Long applicationId,
            @Parameter(name = "userId", description = "지원자 본인 ID", required = true)
            @RequestParam Long userId
    );

    @Operation(
            summary = "지원 상태 변경 (수락/거절)",
            description = "게시글 작성자가 지원자의 지원을 수락하거나 거절합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상태 변경 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "APPLICATION_NOT_FOUND (지원서 ID가 존재하지 않음) 또는 USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "NO_AUTHORITY (권한 없음)"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CAPACITY_FULL (모집 인원 마감, 수락 시에만 해당)"
            )
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateApplicationStatus(
            @Parameter(name = "applicationId", description = "상태를 변경할 지원서 ID", required = true)
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusUpdateRequest request,
            @Parameter(name = "userId", description = "게시글 작성자 ID", required = true)
            @RequestParam Long userId
    );

    @Operation(
            summary = "지원 내용 수정",
            description = "지원자가 지원 내용을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "내용 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "APPLICATION_NOT_FOUND (지원서 ID가 존재하지 않음) 또는 USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "NO_AUTHORITY (권한 없음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<Void>> updateApplicationContent(
            @Parameter(name = "applicationId", description = "내용을 수정할 지원서 ID", required = true)
            @PathVariable Long applicationId,
            @RequestBody ApplicationUpdateRequest request);

    @Operation(
            summary = "지원 삭제",
            description = "지원자가 지원을 삭제(취소)합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "지원 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "APPLICATION_NOT_FOUND (지원서 ID가 존재하지 않음) 또는 USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "NO_AUTHORITY (권한 없음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteApplication(
            @Parameter(name = "applicationId", description = "삭제할 지원서 ID", required = true)
            @PathVariable Long applicationId,
            @Parameter(name = "userId", description = "지원자 ID", required = true)
            @RequestParam Long userId);
}
