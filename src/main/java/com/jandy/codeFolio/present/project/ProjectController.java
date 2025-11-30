package com.jandy.codeFolio.present.project;

import com.jandy.codeFolio.application.project.ProjectService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.project.dto.ProjectResponse;
import com.jandy.codeFolio.present.project.dto.ProjectUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/projects")
public class ProjectController implements ProjectControllerDocs {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<ProjectResponse>>> findAllProjects(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, projectService.findAllByUserId(userId)));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponseWrapper<ProjectResponse>> getProjectById(@PathVariable Long userId, @PathVariable Long projectId) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, projectService.getProjectById(projectId)));
    }

    @PostMapping("/{projectId}/update")
    public ResponseEntity<ApiResponseWrapper<Long>> updateProject(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody ProjectUpdateRequest request) {
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, projectService.updateProject(projectId, request)));
    }
}
