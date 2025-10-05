package com.jandy.codeFolio.present.email;

import com.jandy.codeFolio.application.email.EmailService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.global.util.Role;
import com.jandy.codeFolio.present.email.dto.EmailRequest;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
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
public class EmailController implements EmailControllerDocs{

    private final EmailService emailService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<ApiResponseWrapper<Void>> sendVerification(@RequestParam String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "인증 메일이 발송되었습니다."));
    }

    @PostMapping("/verify")
    public String verifyCode(
            @RequestBody EmailRequest emailRequest,
            HttpSession session
    ) {
        GithubUserResponse tempUser = (GithubUserResponse) session.getAttribute("tempGithubUser");
        if (tempUser == null) throw new CodeFolioRuntimeException(ErrorCode.SESSION_EXPIRED);

        boolean verified = emailService.verifyCode(emailRequest.getEmail(), emailRequest.getCode());
        if (!verified) throw new CodeFolioRuntimeException(ErrorCode.USER_CODE_INVALID);

        User newUser = User.builder()
                .githubId(tempUser.getId())
                .githubName(tempUser.getLogin())
                .email(emailRequest.getEmail())
                .emailVerified(true)
                .name(tempUser.getName())
                .role(Role.STUDENT)
                .isPublic(true)
                .emailVerified(true)
                .build();

        userRepository.save(newUser);

        session.removeAttribute("tempGithubUser");
        session.setAttribute("loginUser", newUser);

        return "redirect:http://localhost:3000/email/signup";
    }
}
