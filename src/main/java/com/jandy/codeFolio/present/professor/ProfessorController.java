package com.jandy.codeFolio.present.professor;

import com.jandy.codeFolio.application.professor.ProfessorService;
import com.jandy.codeFolio.domain.user.User;
import com.jandy.codeFolio.global.exception.CodeFolioRuntimeException;
import com.jandy.codeFolio.global.exception.ErrorCode;
import com.jandy.codeFolio.present.professor.dto.ProfessorLoginRequest;
import com.jandy.codeFolio.present.professor.dto.ProfessorLoginResponse;
import com.jandy.codeFolio.present.professor.dto.ProfessorSignupRequest;
import com.jandy.codeFolio.present.professor.dto.ProfessorSignupResponse;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "교수 API", description = "교수 관련 API")
@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseWrapper<ProfessorSignupResponse>> signup(@RequestBody ProfessorSignupRequest request) {
        User professor = professorService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseWrapper.success(HttpStatus.CREATED, ProfessorSignupResponse.from(professor)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseWrapper<ProfessorLoginResponse>> login(@RequestBody ProfessorLoginRequest request) {
        User professor = professorService.login(request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, ProfessorLoginResponse.from(professor)));
    }
}
