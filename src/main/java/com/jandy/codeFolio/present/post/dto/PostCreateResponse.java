package com.jandy.codeFolio.present.post.dto;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.global.util.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostCreateResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Role role;
    private LocalDate endDate;
    private List<String> skills;

    public static PostCreateResponse from(Post post) {
        PostCreateResponse response = new PostCreateResponse();
        response.setId(post.getId());
        response.setUserId(post.getUser().getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setEndDate(post.getEndDate());
        response.setRole(post.getRole());

        if (post.getSkills() != null) {
            response.setSkills(
                    post.getSkills().stream()
                            .map(Skill::getName)
                            .collect(Collectors.toList())
            );
        } else {
            response.setSkills(List.of());
        }

        return response;
    }
}
