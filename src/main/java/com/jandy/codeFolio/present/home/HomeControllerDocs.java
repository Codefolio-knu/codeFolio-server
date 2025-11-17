package com.jandy.codeFolio.present.home;

import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.home.dto.HomeScreenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Home API", description = "홈 화면 관련 API 명세")
public interface HomeControllerDocs {

    @Operation(
            summary = "홈 화면 조회",
            description = "홈 화면에 표시될 사용자 목록을 페이지네이션으로 조회합니다. 공개된 사용자만 조회됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홈 화면 조회 성공"),
    })
    ResponseEntity<ApiResponseWrapper<HomeScreenResponse>> getHomeScreen(
            @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", required = true)
            int page
    );
}
