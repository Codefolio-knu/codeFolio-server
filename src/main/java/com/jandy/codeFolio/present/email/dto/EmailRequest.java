package com.jandy.codeFolio.present.email.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String code;
    private String email;
}
