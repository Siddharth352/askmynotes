package com.askmynotes.askmynotes.entities;

import com.askmynotes.askmynotes.enums.IndexingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.Instant;
import java.util.UUID;

@Entity
public class KnowledgeDocument {

    @Id
    public UUID id;
    public long sizeByte;
    public String docName;
    public Instant createdAt;
    private IndexingStatus status;
    @Column(columnDefinition = "TEXT")
    public String content;

    public KnowledgeDocument(UUID id, long sizeByte, String docName, String content) {
        this.id = id;
        this.sizeByte = sizeByte;
        this.docName = docName;
        this.content = content;
        this.createdAt = Instant.now();
    }
    public void setIndexingStatus(IndexingStatus status) {
        this.status = status;
    }

    public KnowledgeDocument() {

    }
}
