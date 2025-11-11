package com.jandy.codeFolio.present.achievement;

import com.jandy.codeFolio.application.achievement.AchievementService;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementListResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementDetailResponse;

import java.util.List;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController implements AchievementControllerDocs {

    private final AchievementService achievementService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<AchievementCreateResponse>> createAchievement(@Valid @RequestBody AchievementCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, achievementService.createAchievement(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<AchievementUpdateResponse>> updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementUpdateRequest request) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, achievementService.updateAchievement(id, request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<AchievementListResponse>>> findAllAchievements(@RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, achievementService.findAllAchievements(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<AchievementDetailResponse>> findAchievementById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, achievementService.findAchievementById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }
}
