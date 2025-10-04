package com.jandy.codeFolio.present.post.dto;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.global.util.Role;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime; // BaseTimeEntity의 등록일자를 위해 필요
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostListResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDate endDate;
    private Role role;
    private int capacity;
    private LocalDateTime createdAt;
    private List<String> skills;

    public static PostListResponse from(Post post) {
        PostListResponse response = new PostListResponse();
        response.setId(post.getId());
        response.setUserId(post.getUser().getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setEndDate(post.getEndDate());
        response.setRole(post.getRole());
        response.setCapacity(post.getCapacity());
        response.setCreatedAt(post.getCreatedAt());

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
