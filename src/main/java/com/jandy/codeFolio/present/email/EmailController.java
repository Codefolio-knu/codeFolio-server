package com.jandy.codeFolio.present.email;

import com.jandy.codeFolio.application.email.EmailService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.email.dto.EmailRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<ApiResponseWrapper<Void>> sendVerification(@RequestParam String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "인증 메일이 발송되었습니다."));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponseWrapper<Void>> verifyCode(
            @RequestBody EmailRequest emailRequest,
            HttpSession session
    ) {
        if (emailService.verifyCode(emailRequest.getEmail(), emailRequest.getCode())) {
            session.setAttribute("verified_email", emailRequest.getEmail());
            return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "인증 완료"));
        } else {

        }

        return null;
    }

}
