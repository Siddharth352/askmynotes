package com.askmynotes.askmynotes.repository;

import com.askmynotes.askmynotes.entities.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {
    List<DocumentChunk> findByDocument_IdOrderByChunkIndexAsc(
            UUID documentId
    );
}
