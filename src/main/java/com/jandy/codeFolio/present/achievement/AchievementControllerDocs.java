package com.jandy.codeFolio.present.achievement;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.achievement.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
                    responseCode = "201",
                    description = "성과 등록 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<AchievementCreateResponse>> createAchievement(@Valid @RequestBody AchievementCreateRequest request);

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
    ResponseEntity<ApiResponseWrapper<AchievementUpdateResponse>> updateAchievement(@PathVariable Long id, @Valid @RequestBody AchievementUpdateRequest request);

    @Operation(
            summary = "개인 성과 목록 조회",
            description = "사용자의 모든 개인 성과 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성과 목록 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<List<AchievementListResponse>>> findAllAchievements(@RequestParam Long userId);

    @Operation(
            summary = "개인 성과 상세 조회",
            description = "개인 성과의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성과 상세 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ACHIEVEMENT_NOT_FOUND (성과 ID가 존재하지 않음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<AchievementDetailResponse>> findAchievementById(@PathVariable Long id);

    @Operation(
            summary = "개인 성과 삭제",
            description = "개인 성과를 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성과 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ACHIEVEMENT_NOT_FOUND (성과 ID가 존재하지 않음)"
            )
    })
    ResponseEntity<ApiResponseWrapper<Void>> deleteAchievement(@PathVariable Long id);
}
