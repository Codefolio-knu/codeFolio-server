package com.jandy.codeFolio.present.project;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.project.dto.ProjectResponse;
import com.jandy.codeFolio.present.project.dto.ProjectUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Project API", description = "프로젝트 API 명세")
public interface ProjectControllerDocs {

    @Operation(summary = "프로젝트 목록 조회", description = "사용자의 프로젝트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "USER_NOT_FOUND (사용자 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<List<ProjectResponse>>> findAllProjects(@PathVariable Long userId);

    @Operation(summary = "프로젝트 상세 조회", description = "특정 프로젝트의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "PROJECT_NOT_FOUND (프로젝트 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<ProjectResponse>> getProjectById(@PathVariable Long userId, @PathVariable Long projectId);

    @Operation(summary = "프로젝트 수정", description = "특정 프로젝트의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "PROJECT_NOT_FOUND (프로젝트 ID가 존재하지 않음)")
    })
    ResponseEntity<ApiResponseWrapper<Long>> updateProject(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody ProjectUpdateRequest request);
}
