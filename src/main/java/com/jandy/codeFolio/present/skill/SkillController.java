package com.jandy.codeFolio.present.skill;

import com.jandy.codeFolio.application.skill.SkillService;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/skills")
public class SkillController implements SkillControllerDocs {

    private final SkillService skillService;

    @PostMapping("/sync")
    public void syncSkills(@PathVariable Long userId) {
        skillService.syncSkills(userId);
    }

    @GetMapping
    public List<SkillResponse> getSkillsByUserId(@PathVariable Long userId) {
        return skillService.getSkillsByUserId(userId);
    }
}
