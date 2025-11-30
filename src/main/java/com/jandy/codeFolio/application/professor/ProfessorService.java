package com.jandy.codeFolio.application.professor;


import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.domain.user.UserRepository;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.global.util.Role;
import com.jandy.codeFolio.present.professor.dto.ProfessorLoginRequest;
import com.jandy.codeFolio.present.professor.dto.ProfessorSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(ProfessorSignupRequest request) {
        User professor = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .bio(request.getBio())
                .isPublic(request.getIsPublic())
                .role(Role.PROFESSOR)
                .emailVerified(true)
                .build();
        return userRepository.save(professor);
    }

    @Transactional(readOnly = true)
    public User login(ProfessorLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CodeFolioRuntimeException(ErrorCode.USER_ALREADY_EXISTS));

        if (user.getRole() != Role.PROFESSOR) {
            throw new CodeFolioRuntimeException(ErrorCode.LOGIN_ROLE_FAILED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CodeFolioRuntimeException(ErrorCode.LOGIN_PASSWORD_FAILED);
        }
        return user;
    }
}
