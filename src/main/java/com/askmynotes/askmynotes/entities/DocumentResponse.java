package com.askmynotes.askmynotes.entities;

import java.time.Instant;
import java.util.UUID;

public class DocumentResponse {
    private UUID id;
    private long sizeByte;
    private String docName;
    private Instant createdAt;

    public DocumentResponse(UUID id, long sizeByte, String docName, Instant createdAt) {
        this.id = id;
        this.sizeByte = sizeByte;
        this.docName = docName;
        this.createdAt = createdAt;
    }
}
