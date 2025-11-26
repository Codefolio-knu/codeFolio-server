package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.application.application.ApplicationService;
import com.jandy.codeFolio.global.util.ApiResponseWrapper;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import com.jandy.codeFolio.present.application.dto.ApplicationStatusUpdateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
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

    @PatchMapping("/{applicationId}/content")
    public ResponseEntity<ApiResponseWrapper<Void>> updateApplicationContent(
            @PathVariable Long applicationId,
            @RequestBody ApplicationUpdateRequest request) {
        applicationService.updateApplicationContent(applicationId, request);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteApplication(
            @PathVariable Long applicationId,
            @RequestParam Long userId) {
        applicationService.deleteApplication(applicationId, userId);
        return ResponseEntity.ok(ApiResponseWrapper.success(HttpStatus.OK, null));
    }
}
