package com.jandy.codeFolio.present.gemini;

import com.jandy.codeFolio.application.gemini.GeminiAIService;
import com.jandy.codeFolio.present.gemini.dto.GeminiRecommendationRequest;
import com.jandy.codeFolio.present.gemini.dto.GeminiRecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiAIController {

    private final GeminiAIService geminiAIService;

    @PostMapping("/recommendations")
    public ResponseEntity<GeminiRecommendationResponse> getRecommendations(@RequestBody GeminiRecommendationRequest request) throws IOException {
        String recommendations = geminiAIService.getRecommendations(request.getSkill());
        return ResponseEntity.ok(new GeminiRecommendationResponse(recommendations));
    }
}
