package com.jandy.codeFolio.present.professor.dto;

import com.jandy.codeFolio.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorSignupResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private Boolean isPublic;

    public static ProfessorSignupResponse from(User user) {
        return new ProfessorSignupResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBio(),
                user.getIsPublic()
        );
    }
}
