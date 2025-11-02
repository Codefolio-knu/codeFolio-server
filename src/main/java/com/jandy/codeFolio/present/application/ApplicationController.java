package com.jandy.codeFolio.present.application;

import com.jandy.codeFolio.application.application.ApplicationService;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateRequest;
import com.jandy.codeFolio.present.application.dto.ApplicationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/applications")
public class ApplicationController implements ApplicationControllerDocs {

    private final ApplicationService applicationService;

    @PostMapping
    public ApplicationCreateResponse createApplication(
            @PathVariable Long postId,
            @RequestBody ApplicationCreateRequest request) {
        return applicationService.createApplication(postId, request);
    }
}
