package com.askmynotes.askmynotes.controller;

import com.askmynotes.askmynotes.sevice.EmbeddingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {
    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping("/test")
    public Map<String, Object> test(@RequestBody EmbeddingRequest request) {
        float[] embeddings = embeddingService.generateEmbedding(request.text());
        System.out.println("SIDLOG:: generated embeddings = " + embeddings);
        return Map.of(
                "dimensions", embeddings.length,
                "firstValue", embeddings[0]
        );
    }
    record EmbeddingRequest(
            String text
    ) {}
}