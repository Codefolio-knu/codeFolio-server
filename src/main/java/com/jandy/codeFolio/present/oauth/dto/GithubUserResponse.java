package com.jandy.codeFolio.present.oauth.dto;

import lombok.Data;

@Data
public class GithubUserResponse {
    private Long id;
    private String login;
    private String name;
    private String email;
}
