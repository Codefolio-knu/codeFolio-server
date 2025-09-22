package com.jandy.codeFolio.domain.user;

import com.jandy.codeFolio.domain.achievement.Achievement;
import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.project.Project;
import com.jandy.codeFolio.global.util.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String githubName;

    @Column(nullable = false)
    private Long githubId;

    @Column(columnDefinition = "TEXT")
    private String accessTokenEncrypted;

    private String scope;

    private LocalDateTime lastSyncedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Application> applications;

    public void updateLoginInfo(String accessToken, String scope) {
        this.accessTokenEncrypted = accessToken;
        this.scope = scope;
        this.lastSyncedAt = LocalDateTime.now();
    }
}
