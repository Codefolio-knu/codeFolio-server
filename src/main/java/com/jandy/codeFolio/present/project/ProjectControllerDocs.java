package com.jandy.codeFolio.present.project;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.project.dto.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Project API", description = "프로젝트 API 명세")
public interface ProjectControllerDocs {

    @Operation(summary = "프로젝트 목록 조회", description = "사용자의 프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<List<ProjectResponse>>> findAllProjects(@PathVariable Long userId);
}
