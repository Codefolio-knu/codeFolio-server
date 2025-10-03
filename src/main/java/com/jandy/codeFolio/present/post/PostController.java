package com.jandy.codeFolio.present.post;

import com.jandy.codeFolio.application.post.PostService;
import com.jandy.codeFolio.present.post.dto.PostCreateRequest;
import com.jandy.codeFolio.present.post.dto.PostCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    
    @PostMapping("/regist")
    public PostCreateResponse createPost(@RequestBody PostCreateRequest request) {
        return postService.createPost(request);
    }
}
