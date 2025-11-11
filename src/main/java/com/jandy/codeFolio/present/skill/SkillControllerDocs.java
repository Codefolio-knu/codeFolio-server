package com.jandy.codeFolio.present.skill;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.skill.dto.SkillResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Skill API", description = "기술 스택 API 명세")
public interface SkillControllerDocs {

    @Operation(summary = "기술 스택 연동", description = "사용자의 GitHub 리포지토리를 기반으로 기술 스택을 연동합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "연동 성공"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<Void>> syncSkills(@PathVariable Long userId);

    @Operation(summary = "기술 스택 조회", description = "사용자의 기술 스택 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<List<SkillResponse>>> getSkillsByUserId(@PathVariable Long userId);
}
