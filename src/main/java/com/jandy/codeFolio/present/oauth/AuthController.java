package com.jandy.codeFolio.present.oauth;

import com.jandy.codeFolio.application.oauth.GithubService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

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
    @Value("${front.base-url}")
    private String frontBaseUrl;

    @GetMapping("/login")
    public String redirectToGithubLogin(HttpSession session) {
//        String state = UUID.randomUUID().toString();
//        session.setAttribute("oauth_state", state);

        String githubAuthUrl = "https://github.com/login/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=read:user,user:email";

        return "redirect:" + githubAuthUrl;
    }

    @GetMapping("/callback")
    public String githubCallback(
            @RequestParam String code,
            HttpSession session
    ) {
//        String sessionState = (String) session.getAttribute("oauth_state");
//        if (sessionState == null || !sessionState.equals(state)) {
//            throw new CodeFolioRuntimeException(ErrorCode.SERVER_ERROR);
//        }

        String accessToken = githubService.getAccessToken(code);
        GithubUserResponse githubUser = githubService.getGithubUser(accessToken);

        Optional<User> existingUser = userRepository.findByGithubId(githubUser.getId());
        if (existingUser.isPresent()) {

            User user = existingUser.get();

            // 이메일 미인증
            if (!user.getEmailVerified()) {
                // session.setAttribute("tempGithubUser", githubUser); // Remove this line
                return "redirect:" + frontBaseUrl + "/email/verify?githubId=" + githubUser.getId() + "&githubName=" + githubUser.getLogin() + "&email=" + githubUser.getEmail();
            }

            // 인증회원 로그인
            session.setAttribute("loginUser", user);
            return "redirect:" + frontBaseUrl;
        } else {
            // 신규회원
            // session.setAttribute("tempGithubUser", githubUser); // Remove this line
            return "redirect:" + frontBaseUrl + "/email/verify?githubId=" + githubUser.getId() + "&githubName=" + githubUser.getLogin() + "&email=" + githubUser.getEmail();
        }
    }

    @GetMapping("/auth/me")
    @ResponseBody
    public ResponseEntity<ApiResponseWrapper<UserResponse>> getAuthenticatedUser(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            UserResponse userResponse = UserResponse.fromEntity(loginUser);
            return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, "성공적으로 유저 정보를 가져왔습니다.", userResponse));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseWrapper.error(HttpStatus.UNAUTHORIZED, "로그인된 유저가 없습니다."));
    }
}