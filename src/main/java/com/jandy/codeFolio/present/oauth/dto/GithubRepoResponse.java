package com.jandy.codeFolio.present.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GithubRepoResponse {
    private String name;
    private String description;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("languages_url")
    private String languagesUrl;
    private boolean fork;
}
