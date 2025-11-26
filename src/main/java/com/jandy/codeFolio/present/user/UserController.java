package com.jandy.codeFolio.present.user;

import com.jandy.codeFolio.application.post.PostService;
import com.jandy.codeFolio.application.user.UserService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.post.dto.PostListResponse;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantListResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDocs{

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseWrapper<UserResponse>> signupUser(
            @RequestBody UserSignupRequest userSignupRequest,
            @RequestParam Long githubId,
            @RequestParam String githubName,
            @RequestParam String email,
            HttpSession session
    ) {
        String accessToken = (String) session.getAttribute("githubAccessToken");
        UserResponse response = userService.signupUser(userSignupRequest, githubId, githubName, email, accessToken);
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

    @GetMapping("/{id}/posts")
    public ResponseEntity<ApiResponseWrapper<Page<PostListResponse>>> findPostsByUserId(@PathVariable Long id, Pageable pageable) {
        Page<PostListResponse> posts = postService.findPostsByUserId(id, pageable);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, posts));
    }

    @GetMapping("/me/posts/{postId}/applicants")
    public ResponseEntity<ApiResponseWrapper<ApplicantListResponse>> getApplicantsForPost(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        ApplicantListResponse response = userService.getApplicantsForPost(postId, userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, response));
    }
}
