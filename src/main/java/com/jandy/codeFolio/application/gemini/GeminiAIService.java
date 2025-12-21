package com.jandy.codeFolio.application.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiAIService {

    private final Client client;
    private final String geminiApiKey;
    private final String geminiModelId = "gemini-pro"; // Define the model ID

    @Autowired
    public GeminiAIService(@Value("${gemini.api.key}") String geminiApiKey) {
        this.geminiApiKey = geminiApiKey;
        this.client = Client.builder().apiKey(geminiApiKey).build();
    }

    public GeminiAIService(Client client, String geminiApiKey) {
        this.client = client;
        this.geminiApiKey = geminiApiKey;
    }

    public String getRecommendations(String skill) throws IOException {
        String promptText = String.format("As an expert in software architecture and development, " +
                "provide a comprehensive list of recommended frameworks and library stacks for a project " +
                "focusing on the skill '%s'. Consider industry best practices, scalability, performance, " +
                "and ease of maintenance. For each recommendation, briefly explain why it's suitable and list key libraries or tools within that stack. " +
                "Structure your response clearly with headings for each recommended stack.", skill);

        GenerateContentResponse response = client.models.generateContent(geminiModelId, promptText, null);

        if (response != null && response.text() != null && !response.text().isEmpty()) {
            return response.text();
        }
        return "No recommendations found.";
    }
}
