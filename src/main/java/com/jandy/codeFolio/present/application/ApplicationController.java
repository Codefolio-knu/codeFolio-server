package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.application.application.ApplicationService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/applications")
public class ApplicationController implements ApplicationControllerDocs {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<ApplicationCreateResponse>> createApplication(
            @PathVariable Long postId,
            @RequestBody ApplicationCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, applicationService.createApplication(postId, request)));
    }
}
