package com.jandy.codeFolio.application.project;

import com.jandy.codeFolio.domain.project.Project;
import com.jandy.codeFolio.domain.project.ProjectRepository;
import com.jandy.codeFolio.domain.skill.Skill;
import com.jandy.codeFolio.domain.skill.SkillRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.project.dto.ProjectResponse;
import com.jandy.codeFolio.present.project.dto.ProjectUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        return projectRepository.findAllByUser(user).stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.PROJECT_NOT_FOUND));
        return ProjectResponse.from(project);
    }

    @Transactional
    public Long updateProject(Long projectId, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.PROJECT_NOT_FOUND));

        project.update(
                request.getTitle(),
                request.getBriefDescription(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getIsAwarded()
        );

        if (request.getSkills() != null) {
            project.getSkills().clear();
            for (String skillName : request.getSkills()) {
                Skill skill = skillRepository.findByName(skillName)
                        .orElseGet(() -> skillRepository.save(Skill.builder().name(skillName).build()));
                project.getSkills().add(skill);
            }
        }

        return projectId;
    }
}
