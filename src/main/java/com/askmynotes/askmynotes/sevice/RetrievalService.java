package com.askmynotes.askmynotes.sevice;

import com.askmynotes.askmynotes.entities.RetrievedChunkResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {
    private final VectorStore vectorStore;

    public RetrievalService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<RetrievedChunkResponse> retrieveTopKResult(int k, String question) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(k)
                        .build()
        );
        return documents.stream().map(
                document -> new RetrievedChunkResponse(
                        document.getId().toString(),
                        document.getText(),
                        document.getMetadata()
                )
        ).toList();
    }

}
