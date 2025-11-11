package com.jandy.codeFolio.application.user;

import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.oauth.dto.GithubUserResponse;
import com.jandy.codeFolio.present.user.dto.UserModifyRequest;
import com.jandy.codeFolio.present.user.dto.UserResponse;
import com.jandy.codeFolio.present.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signupUser(UserSignupRequest userSignupRequest, HttpSession session) {
        GithubUserResponse tempUser = (GithubUserResponse) session.getAttribute("tempGithubUser");

        if (tempUser == null) {
            throw new CodeFolioRuntimeException(ErrorCode.SESSION_EXPIRED);
        }

        User newUser = User.builder()
                .githubId(tempUser.getId())
                .email(tempUser.getEmail())
                .studentId(userSignupRequest.getStudentId())
                .major(userSignupRequest.getMajor())
                .name(userSignupRequest.getName())
                .year(userSignupRequest.getYear())
                .bio(userSignupRequest.getBio())
                .isPublic(userSignupRequest.getIsPublic())
                .build();

        userRepository.save(newUser);
        session.removeAttribute("tempGithubUser");

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
}

