package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.global.util.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Application> applications;

}
