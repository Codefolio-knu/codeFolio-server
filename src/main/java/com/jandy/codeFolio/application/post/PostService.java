package com.jandy.codeFolio.application.post;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.post.PostRespository;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.skill.SkillRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.post.dto.PostCreateRequest;
import com.jandy.codeFolio.present.post.dto.PostCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRespository postRespository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

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
                .skills(selectedSkills)
                .build();

        Post savedPost = postRespository.save(newPost);

        return PostCreateResponse.from(savedPost);
    }
}
