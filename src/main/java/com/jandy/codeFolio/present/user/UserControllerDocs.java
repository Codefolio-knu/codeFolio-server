package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User API", description = "사용자 관련 API 명세")
public interface UserControllerDocs {

    @Operation(
            summary = "유저 회원가입",
            description = "Github 인증 후 임시 세션에 저장된 정보를 기반으로 사용자를 회원가입 처리합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "세션 만료 또는 인증 실패",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(
            @RequestBody UserSignupRequest userSignupRequest,
            @Parameter(description = "GitHub 사용자 ID", required = true, in = ParameterIn.QUERY) Long githubId,
            @Parameter(description = "GitHub 사용자 이름", required = true, in = ParameterIn.QUERY) String githubName,
            @Parameter(description = "GitHub 사용자 이메일", required = true, in = ParameterIn.QUERY) String email
    );

    @Operation(
            summary = "유저 정보 수정",
            description = "사용자 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseWrapper<UserResponse>> modifyUser(@RequestBody UserModifyRequest request, @PathVariable Long id);

    @Operation(
            summary = "유저 정보 조회",
            description = "사용자 ID를 통해 유저 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseWrapper<UserResponse>> getUser(@PathVariable Long id);

    @Operation(
            summary = "특정 게시물에 대한 지원자 목록 조회",
            description = "게시물 작성자가 자신의 게시물에 지원한 지원자 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "지원자 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ApplicantListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN (게시물 작성자가 아님)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "POST_NOT_FOUND (게시물 ID가 존재하지 않음)",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseWrapper<ApplicantListResponse>> getApplicantsForPost(
            @Parameter(description = "게시물 ID", required = true, in = ParameterIn.PATH) @PathVariable Long postId,
            @Parameter(description = "사용자(게시물 작성자) ID", required = true, in = ParameterIn.QUERY) @RequestParam Long userId);
}
