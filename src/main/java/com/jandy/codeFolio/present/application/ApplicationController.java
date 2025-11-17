package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.application.application.ApplicationService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import com.jandy.codeFolio.present.application.dto.ApplicationStatusUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController implements ApplicationControllerDocs {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<ApplicationCreateResponse>> createApplication(
            @RequestBody ApplicationCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(HttpStatus.CREATED, applicationService.createApplication(request.getPostId(), request)));
    }

    @PatchMapping("/{applicationId}")
    public ResponseEntity<ApiResponseWrapper<Void>> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody ApplicationStatusUpdateRequest request,
            @RequestParam Long userId) {
        applicationService.updateApplicationStatus(applicationId, request, userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }
}
