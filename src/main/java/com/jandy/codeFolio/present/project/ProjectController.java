package com.jandy.codeFolio.present.project;

import com.jandy.codeFolio.application.project.ProjectService;
import com.jandy.codeFolio.present.project.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/projects")
public class ProjectController implements ProjectControllerDocs {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> findAllProjects(@PathVariable Long userId) {
        return projectService.findAllByUserId(userId);
    }
}
