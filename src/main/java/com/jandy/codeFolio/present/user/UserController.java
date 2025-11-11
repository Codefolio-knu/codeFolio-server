package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.application.user.UserService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDocs{

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(@RequestBody UserSignupRequest userSignupRequest, HttpSession session) {
        UserResponse response = userService.signupUser(userSignupRequest, session);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<UserResponse>> modifyUser(@RequestBody UserModifyRequest request, @PathVariable Long id) {
        UserResponse modifyUser = userService.modifyUser(id, request);
        return  ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, modifyUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUser(id);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, userResponse));
    }
}
