package com.jandy.codeFolio.present.home;

import com.jandy.codeFolio.application.home.HomeService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.home.dto.HomeScreenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController implements HomeControllerDocs {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<HomeScreenResponse>> getHomeScreen(@RequestParam(defaultValue = "0") int page) {
        HomeScreenResponse response = homeService.getHomeScreen(page);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, response));
    }
}
