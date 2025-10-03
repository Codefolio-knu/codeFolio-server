package com.jandy.codeFolio.domain.skill;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder
    public Skill(String name) {
        this.name = name;
    }
}
