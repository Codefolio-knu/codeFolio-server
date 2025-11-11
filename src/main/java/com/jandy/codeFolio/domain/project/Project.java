package com.jandy.codeFolio.domain.project;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String repoUrl;

    public void update(String description, String repoUrl) {
        this.description = description;
        this.repoUrl = repoUrl;
    }
}

