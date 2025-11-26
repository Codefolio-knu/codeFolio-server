package com.jandy.codeFolio.application.post;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.post.PostRepository;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.skill.SkillRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.post.dto.PostCreateRequest;
import com.jandy.codeFolio.present.post.dto.PostCreateResponse;
import com.jandy.codeFolio.present.post.dto.PostDetailResponse;
import com.jandy.codeFolio.present.post.dto.PostListResponse;
import com.jandy.codeFolio.present.post.dto.PostSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        List<Skill> selectedSkills = skillRepository.findAllById(request.getSkillIds());

        if (selectedSkills.size() != request.getSkillIds().size()) {
            throw new CodeFolioRuntimeException(ErrorCode.SKILL_NOT_FOUND);
        }

        Post newPost = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .endDate(request.getEndDate())
                .role(user.getRole())
                .capacity(request.getCapacity())
                .skills(selectedSkills)
                .build();

        Post savedPost = postRepository.save(newPost);

        return PostCreateResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> findAllPosts(PostSearchCondition condition, Pageable pageable) {
        Page<Post> postPage = postRepository.findPostsByConditions(condition, pageable);
        return postPage.map(PostListResponse::from);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.POST_NOT_FOUND));
        return PostDetailResponse.fromEntity(post);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> findPostsByUserId(Long userId, Pageable pageable) {

        if (!userRepository.existsById(userId)) {
            throw new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Post> posts = postRepository.findAllByUserId(userId, pageable);

        return posts.map(PostListResponse::from);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new CodeFolioRuntimeException(ErrorCode.POST_NOT_FOUND);
        }
        postRepository.deleteById(id);
    }
}
