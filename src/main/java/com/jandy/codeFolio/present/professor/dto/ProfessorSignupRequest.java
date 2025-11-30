package com.jandy.codeFolio.present.professor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfessorSignupRequest {
    private String name;
    private String email;
    private String password;
    private String bio;
    private Boolean isPublic;
}
