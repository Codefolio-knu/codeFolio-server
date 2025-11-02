package com.jandy.codeFolio.domain.achievement;

import com.jandy.codeFolio.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByUser(User user);
}
