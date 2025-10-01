package com.jandy.codeFolio.present.oauth;

import com.jandy.codeFolio.application.oauth.GithubService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/oauth/github")
@RequiredArgsConstructor
public class AuthController implements OauthControllerDocs{

    private final GithubService githubService;
    private final UserRepository userRepository;

    @Value("${spring.oauth2.github.client.id}")
    private String clientId;
    @Value("${spring.oauth2.github.client.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public String redirectToGithubLogin(HttpSession session) {
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauth_state", state);

        String githubAuthUrl = "https://github.com/login/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=read:user,user:email"
                + "&state=" + state;

        return "redirect:" + githubAuthUrl;
    }

    @GetMapping("/callback")
    public String githubCallback(
            @RequestParam String code,
            @RequestParam String state,
            HttpSession session
    ) {
        String sessionState = (String) session.getAttribute("oauth_state");
        if (sessionState == null || !sessionState.equals(state)) {
            throw new CodeFolioRuntimeException(ErrorCode.SERVER_ERROR);
        }

        String verifiedEmail = (String) session.getAttribute("verified_email");
        if (verifiedEmail == null) {
            throw new CodeFolioRuntimeException(ErrorCode.USER_MAIL_NOTFOUND);
        }

        String accessToken = githubService.getAccessToken(code);

        GithubUserResponse githubUser = githubService.getGithubUser(accessToken);
        githubUser.setEmail(verifiedEmail);

        Optional<User> existingUser = userRepository.findByGithubId(githubUser.getId());
        if (existingUser.isPresent()) {
            // 로그인 회원
            session.setAttribute("loginUser", existingUser.get());
            return "redirect:http://localhost:3000/home";
        } else {
            // 신규회원
            session.setAttribute("tempGithubUser", githubUser);
            return "redirect:http://localhost:3000/signup";
        }
    }
}