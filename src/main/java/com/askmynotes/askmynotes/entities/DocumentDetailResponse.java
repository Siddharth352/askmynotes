package com.askmynotes.askmynotes.entities;

import java.time.Instant;
import java.util.UUID;

public class DocumentDetailResponse {
    private UUID id;
    private long sizeByte;
    private String docName;
    private Instant createdAt;
    private String content;
}
