package com.askmynotes.askmynotes.sevice;

import com.askmynotes.askmynotes.repository.DocumentChunkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ChunkingService {
    private static final int CHUNK_SIZE = 500;
    private static final int CHUNK_OVERLAP = 50;

    private DocumentChunkRepository documentChunkRepository;

    public ChunkingService(DocumentChunkRepository repository) {
        this.documentChunkRepository = repository;
    }

    public List<TextChunk> split(String content) {
        List<String> words = Arrays.stream(content.trim().split("\\s+"))
                .filter(word -> !word.isBlank())
                .toList();
        List<TextChunk> chunks = new ArrayList<>();

        int start = 0;
        int chunkIndex = 0;

        while (start< words.size()) {
            int end = Math.min(start + CHUNK_SIZE, words.size());

            List<String> chunkWords = words.subList(start, end);
            chunks.add(
                    new TextChunk(
                            chunkIndex,
                            String.join(" ", chunkWords),
                            chunkWords.size()
                    )
            );
            chunkIndex += 1;
            if (end == words.size()) {
                break;
            }
            start = end - CHUNK_OVERLAP;
        }
        return chunks;
    }

    public List<DocumentChunkResponse> getChunksForDoc(UUID docId) {
        return documentChunkRepository.findByDocument_IdOrderByChunkIndexAsc(docId)
                .stream()
                .map(documentChunk -> new DocumentChunkResponse(
                        documentChunk.id,
                        documentChunk.chunkIndex,
                        documentChunk.wordCnt,
                        documentChunk.content
                )).toList();
    }

    public record TextChunk(
            int index,
            String content,
            int wordCount
    ) {}

    public record DocumentChunkResponse(
            UUID id,
            int chunkIndex,
            int wordCount,
            String content
    ) {}
}
