package com.jandy.codeFolio.present.user.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicantListResponse {
    private List<ApplicantInfoResponse> applicants;
}
