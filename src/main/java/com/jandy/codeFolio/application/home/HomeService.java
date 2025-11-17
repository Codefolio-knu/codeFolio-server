package com.jandy.codeFolio.application.home;

import com.jandy.codeFolio.domain.achievement.Achievement;
import com.jandy.codeFolio.domain.achievement.AchievementType;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.present.home.dto.AchievementSummaryResponse;
import com.jandy.codeFolio.present.home.dto.HomeScreenResponse;
import com.jandy.codeFolio.present.home.dto.SkillSummaryResponse;
import com.jandy.codeFolio.present.home.dto.UserSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final UserRepository userRepository;

    public HomeScreenResponse getHomeScreen(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<User> users = userRepository.findAllByIsPublic(true, pageable);

        List<UserSummaryResponse> userSummaries = users.getContent().stream()
                .map(this::mapToUserSummaryResponse)
                .collect(Collectors.toList());

        return new HomeScreenResponse(userSummaries);
    }

    private UserSummaryResponse mapToUserSummaryResponse(User user) {
        List<SkillSummaryResponse> skills = user.getSkills().stream()
                .limit(4)
                .map(skill -> new SkillSummaryResponse(skill.getName()))
                .collect(Collectors.toList());

        List<AchievementSummaryResponse> achievements = getAchievements(user);

        return new UserSummaryResponse(
                user.getName(),
                user.getBio(),
                user.getMajor(),
                skills,
                achievements
        );
    }

    private List<AchievementSummaryResponse> getAchievements(User user) {
        List<Achievement> projectAchievements = user.getAchievements().stream()
                .filter(a -> a.getType() == AchievementType.PROJECT)
                .limit(1)
                .collect(Collectors.toList());

        List<Achievement> contestAchievements = user.getAchievements().stream()
                .filter(a -> a.getType() == AchievementType.CONTEST)
                .limit(2)
                .collect(Collectors.toList());

        return Stream.concat(projectAchievements.stream(), contestAchievements.stream())
                .map(a -> new AchievementSummaryResponse(a.getTitle(), a.getType()))
                .collect(Collectors.toList());
    }
}
