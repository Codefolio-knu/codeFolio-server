package com.jandy.codeFolio.domain.achievement;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "achievements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Achievement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType type;

    @Column(nullable = false)
    private String title;

    @Column(length = 100)
    private String briefDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
    private String link;
    private String host;
    private String award;
    private Boolean isAwarded;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "achievement_skill",
            joinColumns = @JoinColumn(name = "achievement_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();


    public void update(AchievementType type, String title, String briefDescription, String description, LocalDate startDate, LocalDate endDate, String link, String host, String award, Boolean isAwarded) {
        this.type = type;
        this.title = title;
        this.briefDescription = briefDescription;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
        this.host = host;
        this.award = award;
        this.isAwarded = isAwarded;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills.clear();
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }
}

    

        

    