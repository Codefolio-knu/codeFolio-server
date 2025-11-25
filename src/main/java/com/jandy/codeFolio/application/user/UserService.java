package com.jandy.codeFolio.application.user;

import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.application.ApplicationRepository;
import com.jandy.codeFolio.domain.post.Post;
import com.jandy.codeFolio.domain.post.PostRepository;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantInfoResponse;
import com.jandy.codeFolio.present.user.dto.mypage.ApplicantListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public UserResponse signupUser(UserSignupRequest userSignupRequest, Long githubId, String githubName, String email) {

        User newUser = User.builder()
                .githubId(githubId)
                .email(email)
                .githubName(githubName)
                .studentId(userSignupRequest.getStudentId())
                .major(userSignupRequest.getMajor())
                .name(userSignupRequest.getName())
                .year(userSignupRequest.getYear())
                .bio(userSignupRequest.getBio())
                .isPublic(userSignupRequest.getIsPublic())
                .emailVerified(true)
                .build();

        userRepository.save(newUser);

        return UserResponse.fromEntity(newUser);
    }

    @Transactional
    public UserResponse modifyUser(Long id, UserModifyRequest request) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        findUser.updateUser(
                request.getMajor(),
                request.getName(),
                request.getYear(),
                request.getBio(),
                request.getIsPublic()
        );

        return UserResponse.fromEntity(findUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public ApplicantListResponse getApplicantsForPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND));

        if (!post.getUser().equals(user)) {
            throw new CodeFolioRuntimeException(ErrorCode.NO_AUTHORITY);
        }

        List<Application> applications = applicationRepository.findAllByPost(post);

        List<ApplicantInfoResponse> applicantInfoResponses = applications.stream()
                .map(ApplicantInfoResponse::from)
                .collect(Collectors.toList());

        return new ApplicantListResponse(applicantInfoResponses);
    }
}

