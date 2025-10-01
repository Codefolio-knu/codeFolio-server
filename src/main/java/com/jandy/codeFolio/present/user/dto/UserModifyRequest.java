package com.jandy.codeFolio.present.user.dto;

import lombok.Data;

@Data
public class UserModifyRequest {
    private String name;
    private String major;
    private Integer year;
    private String bio;
    private Boolean isPublic;
}
