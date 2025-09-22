package com.jandy.codeFolio.domain.achievement;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "achievements")
public class Achievement extends BaseTimeEntity {

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

    private String certificateUrl;

    private LocalDate achievedAt;
}

