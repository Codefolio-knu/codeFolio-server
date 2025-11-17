package com.jandy.codeFolio.present.user.dto.mypage;

import com.jandy.codeFolio.domain.application.Application;
import com.jandy.codeFolio.domain.application.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantInfoResponse {
    private Long applicationId;
    private String name;
    private String major;
    private int grade;
    private LocalDateTime createdAt;
    private String content;
    private ApplicationStatus status;

    public static ApplicantInfoResponse from(Application application) {
        return ApplicantInfoResponse.builder()
                .applicationId(application.getId())
                .name(application.getUser().getName())
                .major(application.getUser().getMajor())
                .grade(application.getUser().getYear())
                .createdAt(application.getCreatedAt())
                .content(application.getContent())
                .status(application.getStatus())
                .build();
    }
}
