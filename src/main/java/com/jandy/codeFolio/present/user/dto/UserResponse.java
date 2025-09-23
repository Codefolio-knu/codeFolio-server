package com.jandy.codeFolio.present.user.dto;

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
}
