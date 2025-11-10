package com.jandy.codeFolio.application.oauth;

import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.util.Role;
import com.jandy.codeFolio.present.oauth.dto.GithubRepoResponse;
import com.jandy.codeFolio.present.oauth.dto.GithubTokenResponse;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.oauth2.github.client.id}") private String clientId;
    @Value("${spring.oauth2.github.client.secret}") private String clientSecret;
    @Value("${spring.oauth2.github.client.redirect-uri}") private String redirectUri;
    @Value("${spring.oauth2.github.urls.access-token}") private String tokenUrl;
    @Value("${spring.oauth2.github.urls.user}") private String userUrl;
    private final String reposUrl = "https://api.github.com/user/repos?per_page=100";

    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<GithubTokenResponse> response = restTemplate.postForEntity(
                tokenUrl, request, GithubTokenResponse.class);

        return response.getBody().getAccessToken();
    }

    public GithubUserResponse getGithubUser(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<GithubUserResponse> response = restTemplate.exchange(
                userUrl, HttpMethod.GET, entity, GithubUserResponse.class);

        return response.getBody();
    }

    public List<GithubRepoResponse> getRepositories(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<GithubRepoResponse[]> response = restTemplate.exchange(
                reposUrl, HttpMethod.GET, entity, GithubRepoResponse[].class);

        return Arrays.asList(response.getBody());
    }

    public Map<String, Long> getLanguages(String languagesUrl, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Long>> response = restTemplate.exchange(
                languagesUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
