package com.jandy.codeFolio.present.post.dto;

import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.global.util.Role;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDate endDate;
    private int capacity;
    private Role role;
    private List<String> skills;
    private UserResponse writer;

    public static PostDetailResponse fromEntity(Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .endDate(post.getEndDate())
                .capacity(post.getCapacity())
                .role(post.getRole())
                .skills(post.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))
                .writer(UserResponse.fromEntity(post.getUser()))
                .build();
    }
}
