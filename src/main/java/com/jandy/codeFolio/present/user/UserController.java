package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.application.user.UserService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserControllerDocs{

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(
            @RequestBody UserSignupRequest userSignupRequest,
            @RequestParam Long githubId,
            @RequestParam String githubName,
            @RequestParam String email
    ) {
        UserResponse response = userService.signupUser(userSignupRequest, githubId, githubName, email);
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

    @GetMapping("/me/posts/{postId}/applicants")
    public ResponseEntity<ApiResponseWrapper<ApplicantListResponse>> getApplicantsForPost(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        ApplicantListResponse response = userService.getApplicantsForPost(postId, userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, response));
    }
}
