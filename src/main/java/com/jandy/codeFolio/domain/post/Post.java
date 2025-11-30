package com.jandy.codeFolio.domain.post;

import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.global.util.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
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

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus status = RecruitmentStatus.RECRUITING;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Application> applications;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_skill_mapping",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @Builder
    public Post(User user, String title, String content, LocalDate endDate, int capacity, Role role, List<Skill> skills) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.endDate = endDate;
        this.capacity = capacity;
        this.role = role;
        this.skills = skills;
        this.status = RecruitmentStatus.RECRUITING;
    }

    public void completeRecruitment() {
        this.status = RecruitmentStatus.COMPLETED;
    }
}
