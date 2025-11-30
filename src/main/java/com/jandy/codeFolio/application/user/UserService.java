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
import com.jandy.codeFolio.present.post.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public UserResponse signupUser(UserSignupRequest userSignupRequest, Long githubId, String githubName, String email, String accessToken) {

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

        newUser.updateLoginInfo(accessToken, "");
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

    @Transactional(readOnly = true)
    public Page<PostListResponse> findAppliedPostsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new CodeFolioRuntimeException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Object[]> appliedDetailsPage = applicationRepository.findAppliedPostDetailsByUserId(userId, pageable);
        List<Long> postIds = appliedDetailsPage.getContent().stream().map(res -> (Long) res[0]).collect(Collectors.toList());
        Map<Long, Long> applicationIdsMap = appliedDetailsPage.getContent().stream().collect(Collectors.toMap(res -> (Long) res[0], res -> (Long) res[1]));

        if (postIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Post> postsWithDetails = postRepository.findPostsWithSkillsByIds(postIds);

        Map<Long, Long> applicantCounts = applicationRepository.countApplicationsByPostIds(postIds).stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]
                ));

        Map<Long, Post> postMap = postsWithDetails.stream()
                .collect(Collectors.toMap(Post::getId, Function.identity()));

        List<PostListResponse> responses = postIds.stream()
                .map(postMap::get)
                .map(post -> {
                    int applicantCount = applicantCounts.getOrDefault(post.getId(), 0L).intValue();
                    Long applicationId = applicationIdsMap.get(post.getId());
                    return PostListResponse.from(post, applicantCount, true, applicationId);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, appliedDetailsPage.getTotalElements());
    }
}

