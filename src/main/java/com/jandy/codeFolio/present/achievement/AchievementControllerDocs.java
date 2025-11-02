package com.jandy.codeFolio.present.achievement;

import com.jandy.codeFolio.present.achievement.dto.AchievementCreateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementCreateResponse;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateRequest;
import com.jandy.codeFolio.present.achievement.dto.AchievementUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Achievement API", description = "개인 성과 API 명세")
public interface AchievementControllerDocs {

    @Operation(
            summary = "개인 성과 등록",
            description = """
                    개인 성과(프로젝트, 수상내역)를 등록합니다.
                    
                    **AchievementType 종류:**
                    - `PROJECT`
                    - `CONTEST`
                    
                    **`PROJECT` 타입 필수 값:**
                    - `userId`
                    - `type`
                    - `title` (프로젝트 이름)
                    - `startDate`
                    - `endDate`
                    
                    **`CONTEST` 타입 필수 값:**
                    - `userId`
                    - `type`
                    - `title` (대회 이름)
                    - `host`
                    - `award`
                    - `endDate` (수상 날짜)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성과 등록 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            )
    })
    AchievementCreateResponse createAchievement(@Valid @RequestBody AchievementCreateRequest request);

    @Operation(
            summary = "개인 성과 수정",
            description = """
                    개인 성과(프로젝트, 수상내역)를 수정합니다.
                    
                    **AchievementType 종류:**
                    - `PROJECT`
                    - `CONTEST`
                    
                    **`PROJECT` 타입 필수 값:**
                    - `userId`
                    - `type`
                    - `title` (프로젝트 이름)
                    - `startDate`
                    - `endDate`
                    
                    **`CONTEST` 타입 필수 값:**
                    - `userId`
                    - `type`
                    - `title` (대회 이름)
                    - `host`
                    - `award`
                    - `endDate` (수상 날짜)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성과 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ACHIEVEMENT_NOT_FOUND (성과 ID가 존재하지 않음)"
            )
    })
    AchievementUpdateResponse updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementUpdateRequest request);
}
