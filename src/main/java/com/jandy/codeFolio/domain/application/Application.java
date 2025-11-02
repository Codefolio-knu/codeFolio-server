package com.jandy.codeFolio.domain.application;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static Application toEntity(Post post, User user, String content) {
        return Application.builder()
                .post(post)
                .user(user)
                .content(content)
                .status(ApplicationStatus.PENDING)
                .build();
    }
}