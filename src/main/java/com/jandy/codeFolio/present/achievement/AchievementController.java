package com.jandy.codeFolio.present.achievement;

import com.jandy.codeFolio.application.achievement.AchievementService;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementListResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementDetailResponse;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController implements AchievementControllerDocs {

    private final AchievementService achievementService;

    @PostMapping
    public AchievementCreateResponse createAchievement(@Valid @RequestBody AchievementCreateRequest request) {
        return achievementService.createAchievement(request);
    }

    @PutMapping("/{id}")
    public AchievementUpdateResponse updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementUpdateRequest request) {
        return achievementService.updateAchievement(id, request);
    }

    @GetMapping
    public List<AchievementListResponse> findAllAchievements(@RequestParam Long userId) {
        return achievementService.findAllAchievements(userId);
    }

    @GetMapping("/{id}")
    public AchievementDetailResponse findAchievementById(@PathVariable Long id) {
        return achievementService.findAchievementById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
    }
}
