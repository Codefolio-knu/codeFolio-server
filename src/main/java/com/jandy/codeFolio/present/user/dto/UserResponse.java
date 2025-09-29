package com.jandy.codeFolio.present.user.dto;

import com.jandy.codeFolio.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String githubName;
    private Long githubId;
    private String scope;
    private String email;
    private Integer studentId;
    private String name;
    private String major;
    private Integer year;
    private String bio;
    private Boolean isPublic;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .githubName(user.getGithubName())
                .githubId(user.getGithubId())
                .scope(user.getScope())
                .email(user.getEmail())
                .studentId(user.getStudentId())
                .name(user.getName())
                .major(user.getMajor())
                .year(user.getYear())
                .bio(user.getBio())
                .isPublic(user.getIsPublic())
                .build();
    }
}
