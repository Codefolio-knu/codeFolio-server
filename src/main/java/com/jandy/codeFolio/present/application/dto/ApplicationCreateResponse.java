package com.jandy.codeFolio.present.application.dto;

import com.jandy.codeFolio.domain.application.Application;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationCreateResponse {
    private Long id;

    public static ApplicationCreateResponse from(Application application) {
        return ApplicationCreateResponse.builder()
                .id(application.getId())
                .build();
    }
}
