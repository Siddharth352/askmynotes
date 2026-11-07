package com.askmynotes.askmynotes.controller;

import com.askmynotes.askmynotes.entities.RetrievedChunkResponse;
import com.askmynotes.askmynotes.entities.SemanticSearchRequest;
import com.askmynotes.askmynotes.sevice.RetrievalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/search")
public class SearchController {
    private final RetrievalService retrievalService;

    public SearchController(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @PostMapping
    public List<RetrievedChunkResponse> getTopKResult(@RequestBody SemanticSearchRequest request) {
        return retrievalService.retrieveTopKResult(request.k(), request.question());
    }
}
