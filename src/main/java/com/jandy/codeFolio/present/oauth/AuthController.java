package com.jandy.codeFolio.present.oauth;

import com.jandy.codeFolio.application.oauth.GithubService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.user.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/oauth/github")
@RequiredArgsConstructor
public class AuthController {

    private final GithubService githubService;

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
    @ResponseBody
    public ResponseEntity<ApiResponseWrapper<UserResponse>> githubCallback(
            @RequestParam String code,
            @RequestParam String state,
            HttpSession session
    ) {
        String sessionState = (String) session.getAttribute("oauth_state");
        if (sessionState == null || !sessionState.equals(state)) {
            throw new RuntimeException("State mismatch. Possible CSRF attack.");
        }

        String accessToken = githubService.getAccessToken(code);

        GithubUserResponse githubUser = githubService.getGithubUser(accessToken);

        User user = githubService.registerOrLoginUser(githubUser, accessToken, "read:user,user:email");
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .githubId(user.getGithubId())
                .githubName(user.getGithubName())
                .scope(user.getScope())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK ,userResponse));
    }


}