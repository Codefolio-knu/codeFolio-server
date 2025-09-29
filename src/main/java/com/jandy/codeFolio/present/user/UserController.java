package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDocs{

    private final UserRepository userRepository;

    @PostMapping("/signup/user")
    @ResponseBody
    public ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(@RequestBody UserSignupRequest userSignupRequest, HttpSession session) {
        GithubUserResponse tempUser = (GithubUserResponse) session.getAttribute("tempGithubUser");

        if (tempUser == null) throw new CodeFolioRuntimeException(ErrorCode.SESSION_EXPIRED);

        User newUser = User.builder()
                .githubId(tempUser.getId())
                .email(tempUser.getEmail())
                .studentId(userSignupRequest.getStudentId())
                .major(userSignupRequest.getMajor())
                .name(userSignupRequest.getName())
                .year(userSignupRequest.getYear())
                .bio(userSignupRequest.getBio())
                .isPublic(userSignupRequest.getIsPublic())
                .build();

        userRepository.save(newUser);
        session.removeAttribute("tempGithubUser");

        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, UserResponse.fromEntity(newUser)));
    }
}
