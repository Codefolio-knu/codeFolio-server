package com.jandy.codeFolio.present.email;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.email.dto.EmailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Email API", description = "이메일 인증 API 명세")
public interface EmailControllerDocs {

    @Operation(summary = "인증 메일 발송", description = "회원 가입을 위한 이메일 인증 코드를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 메일 발송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 이메일 형식 또는 요청 데이터")
    })
    ResponseEntity<ApiResponseWrapper<Void>> sendVerification(
            @Parameter(description = "인증할 이메일 주소", example = "test@knu.ac.kr") @RequestParam String email
    );

    @Operation(summary = "이메일 인증 코드 확인", description = "이메일 주소와 인증 코드를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 확인 성공"),
            @ApiResponse(responseCode = "400", description = "인증 코드 불일치 또는 잘못된 요청 데이터")
    })
    String verifyCode(
            @Parameter(description = "이메일 및 인증 코드") @RequestBody EmailRequest emailRequest,
            @Parameter(hidden = true) HttpSession session
    );
}