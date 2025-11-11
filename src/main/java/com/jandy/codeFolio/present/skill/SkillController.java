package com.jandy.codeFolio.present.skill;

import com.jandy.codeFolio.application.skill.SkillService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/skills")
public class SkillController implements SkillControllerDocs {

    private final SkillService skillService;

    @PostMapping("/sync")
    public ResponseEntity<ApiResponseWrapper<Void>> syncSkills(@PathVariable Long userId) {
        skillService.syncSkills(userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<SkillResponse>>> getSkillsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, skillService.getSkillsByUserId(userId)));
    }
}
