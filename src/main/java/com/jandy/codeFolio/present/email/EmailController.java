package com.jandy.codeFolio.present.email;

import com.jandy.codeFolio.application.email.EmailService;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.email.dto.EmailRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController implements EmailControllerDocs{

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Value("${front.base-url}")
    private String frontBaseUrl;

    @PostMapping("/send")
    public ResponseEntity<ApiResponseWrapper<Void>> sendVerification(@RequestParam String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "인증 메일이 발송되었습니다."));
    }

    @PostMapping("/verify")
    public String verifyCode(
            @RequestBody EmailRequest emailRequest,
            @RequestParam Long githubId,
            @RequestParam String githubName,
            HttpSession session
    ) {
        // GithubUserResponse tempUser = (GithubUserResponse) session.getAttribute("tempGithubUser"); // Remove this line
        // if (tempUser == null) throw new CodeFolioRuntimeException(ErrorCode.SESSION_EXPIRED); // Remove this line

        boolean verified = emailService.verifyCode(emailRequest.getEmail(), emailRequest.getCode());
        if (!verified) throw new CodeFolioRuntimeException(ErrorCode.USER_CODE_INVALID);

        // User newUser = User.builder() // Remove user creation from here
        //         .githubId(githubId)
        //         .githubName(githubName)
        //         .email(emailRequest.getEmail())
        //         .emailVerified(true)
        //         .name(githubName)
        //         .role(Role.STUDENT)
        //         .isPublic(true)
        //         .emailVerified(true)
        //         .build();

        // userRepository.save(newUser); // Remove user saving from here

        // session.removeAttribute("tempGithubUser"); // Remove this line
        // session.setAttribute("loginUser", newUser); // Remove this line

        return "redirect:" + frontBaseUrl + "/signup?githubId=" + githubId + "&githubName=" + githubName + "&email=" + emailRequest.getEmail();
    }
}
