package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(UserSignupRequest userSignupRequest, HttpSession session);
}
