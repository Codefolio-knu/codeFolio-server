package com.jandy.codeFolio.present.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeScreenResponse {
    private List<UserSummaryResponse> users;
}
