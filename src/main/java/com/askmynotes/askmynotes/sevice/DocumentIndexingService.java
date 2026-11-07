package com.askmynotes.askmynotes.sevice;

import com.askmynotes.askmynotes.entities.DocumentChunk;
import com.askmynotes.askmynotes.repository.DocumentChunkRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentIndexingService {
    private final DocumentChunkRepository chunkRepository;
    private final VectorStore vectorStore;

    public DocumentIndexingService(DocumentChunkRepository chunkRepository, VectorStore vectorStore) {
        this.chunkRepository = chunkRepository;
        this.vectorStore = vectorStore;
    }

    public int indexDocument(UUID documentId) {
        List<DocumentChunk> chunks = chunkRepository.findByDocument_IdOrderByChunkIndexAsc(documentId);
        List<Document> vectorDocuments = chunks.stream()
                .map(chunk -> Document.builder()
                        .id(chunk.id.toString())
                        .text(chunk.content)
                        .metadata(Map.of(
                                "documentId", documentId.toString(),
                                "chunkId", chunk.id.toString(),
                                "chunkIndex", chunk.chunkIndex
                        ))
                        .build()
                ).toList();
        vectorStore.add(vectorDocuments);
        return vectorDocuments.size();
    }
}
