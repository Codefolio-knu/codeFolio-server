package com.jandy.codeFolio.domain.achievement;

import com.jandy.codeFolio.domain.base.BaseTimeEntity;
import com.jandy.codeFolio.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String link;

    private String host;

    private String award;

    

            private Boolean isAwarded;

    

        

    

            public void update(AchievementType type, String title, String description, LocalDate startDate, LocalDate endDate, String link, String host, String award, Boolean isAwarded) {

    

                this.type = type;

    

                this.title = title;

    

                this.description = description;

    

                this.startDate = startDate;

    

                this.endDate = endDate;

    

                this.link = link;

    

                this.host = host;

    

                this.award = award;

    

                this.isAwarded = isAwarded;

    

            }

    

        }

    

        

    