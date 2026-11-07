package com.askmynotes.askmynotes.entities;

import java.util.UUID;

public record IndexedDocumentResponse(
        UUID documentId,
        int indexedChunks,
        String status
) {}
