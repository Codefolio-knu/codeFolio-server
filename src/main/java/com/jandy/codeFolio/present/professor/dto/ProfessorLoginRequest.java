package com.jandy.codeFolio.present.professor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfessorLoginRequest {
    private String email;
    private String password;
}
