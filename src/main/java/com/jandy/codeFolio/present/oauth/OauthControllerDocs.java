package com.jandy.codeFolio.present.oauth;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "github oauth API", description = "github 로그인 API 명세")
public interface OauthControllerDocs {

    @Operation(summary = "Github 로그인", description = "Github 로그인 페이지로 리다이렉트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "리다이렉트 성공")
    })
    String redirectToGithubLogin(HttpSession session);

    @Hidden
    String githubCallback(
            String code,
            HttpSession session
    );

    @Operation(summary = "인증된 유저 정보 조회", description = "현재 로그인된 유저의 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    ResponseEntity<ApiResponseWrapper<UserResponse>> getAuthenticatedUser(HttpSession session);

    @Operation(summary = "로그아웃", description = "현재 세션을 무효화하여 로그아웃합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")

    })
    ResponseEntity<ApiResponseWrapper<String>> logout(HttpSession session);
}