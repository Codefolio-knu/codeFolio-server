package com.jandy.codeFolio.present.user.dto;

import lombok.Data;

@Data
public class UserSignupRequest {
    private Integer studentId;
    private String name;
    private String major;
    private Integer year;
    private String bio;
    private Boolean isPublic;
}
