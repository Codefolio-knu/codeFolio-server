package com.jandy.codeFolio.domain.project;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(length = 100)
    private String briefDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String repoUrl;

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean isAwarded;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_skill",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    public void update(String description, String repoUrl) {
        this.description = description;
        this.repoUrl = repoUrl;
    }

    public void update(String title, String briefDescription, String description, LocalDate startDate, LocalDate endDate, Boolean isAwarded) {
        this.title = title;
        this.briefDescription = briefDescription;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAwarded = isAwarded;
    }
}

