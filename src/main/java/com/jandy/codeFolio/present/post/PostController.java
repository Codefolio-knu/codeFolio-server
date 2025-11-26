package com.jandy.codeFolio.present.post;

import com.jandy.codeFolio.application.post.PostService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.post.dto.PostCreateRequest;
import com.jandy.codeFolio.present.post.dto.PostCreateResponse;
import com.jandy.codeFolio.present.post.dto.PostDetailResponse;
import com.jandy.codeFolio.present.post.dto.PostListResponse;
import com.jandy.codeFolio.present.post.dto.PostSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController implements PostControllerDocs{

    private final PostService postService;
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<PostCreateResponse>> createPost(@RequestBody PostCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, postService.createPost(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<Page<PostListResponse>>> findAllPosts(
            @RequestParam(required = false) List<Long> skillIds,
            @RequestParam(required = false) Integer capacity,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        PostSearchCondition condition = new PostSearchCondition();
        condition.setSkillIds(skillIds);
        condition.setCapacity(capacity);

        Page<PostListResponse> posts = postService.findAllPosts(condition, pageable);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<PostDetailResponse>> findPostById(@PathVariable Long id) {
        PostDetailResponse post = postService.findPostById(id);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }
}
