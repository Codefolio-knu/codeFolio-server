package com.jandy.codeFolio.present.achievement;

import com.jandy.codeFolio.application.achievement.AchievementService;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
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
}
