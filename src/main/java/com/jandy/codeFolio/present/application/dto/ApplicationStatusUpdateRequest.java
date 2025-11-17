package com.jandy.codeFolio.present.application.dto;

import com.jandy.codeFolio.domain.application.ApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationStatusUpdateRequest {
    private ApplicationStatus status;
}
