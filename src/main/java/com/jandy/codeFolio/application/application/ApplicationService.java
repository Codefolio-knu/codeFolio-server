package com.jandy.codeFolio.application.application;

import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.application.ApplicationRepository;
import com.jandy.codeFolio.domain.application.ApplicationStatus;
import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.post.PostRepository;
import com.jandy.codeFolio.domain.post.RecruitmentStatus;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import com.jandy.codeFolio.present.application.dto.ApplicationStatusUpdateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApplicationCreateResponse createApplication(Long postId, ApplicationCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        if (!user.getIsPublic()) {
            throw new CodeFolioRuntimeException(ErrorCode.USER_NOT_PUBLIC);
        }

        if (post.getUser().equals(user)) {
            throw new CodeFolioRuntimeException(ErrorCode.CANNOT_APPLY_TO_OWN_POST);
        }

        if (applicationRepository.existsByPostAndUser(post, user)) {
            throw new CodeFolioRuntimeException(ErrorCode.USER_ALREADY_APPLIED);
        }

        long acceptedCount = applicationRepository.countByPostAndStatus(post, ApplicationStatus.ACCEPTED);
        if (post.getStatus() == RecruitmentStatus.COMPLETED || post.getCapacity() <= acceptedCount) {
            throw new CodeFolioRuntimeException(ErrorCode.RECRUITMENT_CLOSED);
        }

        Application application = Application.toEntity(post, user, request.getContent());
        Application savedApplication = applicationRepository.save(application);

        return ApplicationCreateResponse.from(savedApplication);
    }

    @Transactional
    public void updateApplicationStatus(Long applicationId, ApplicationStatusUpdateRequest request, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.APPLICATION_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        Post post = application.getPost();

        if (!post.getUser().equals(user)) {
            throw new CodeFolioRuntimeException(ErrorCode.NO_AUTHORITY);
        }

        if (request.getStatus() == ApplicationStatus.ACCEPTED) {
            long acceptedCount = applicationRepository.countByPostAndStatus(post, ApplicationStatus.ACCEPTED);
            if (post.getCapacity() <= acceptedCount) {
                throw new CodeFolioRuntimeException(ErrorCode.CAPACITY_FULL);
            }
        }

        application.changeStatus(request.getStatus());
    }

    @Transactional
    public void updateApplicationContent(Long applicationId, ApplicationUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.APPLICATION_NOT_FOUND));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        if (!application.getUser().equals(user)) {
            throw new CodeFolioRuntimeException(ErrorCode.NO_AUTHORITY);
        }

        application.updateContent(request.getContent());
    }

    @Transactional
    public void deleteApplication(Long applicationId, Long userId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.APPLICATION_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        if (!application.getUser().equals(user)) {
            throw new CodeFolioRuntimeException(ErrorCode.NO_AUTHORITY);
        }

        applicationRepository.delete(application);
    }
}
