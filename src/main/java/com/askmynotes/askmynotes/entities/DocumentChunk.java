package com.askmynotes.askmynotes.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "document_chunk",
        uniqueConstraints = @UniqueConstraint(
                name = "doc_chunk_index",
                columnNames = {"document_id", "chunk_index"}
        )
)
public class DocumentChunk {

    @Id
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    public KnowledgeDocument document;

    @Column(name = "chunk_index")
    public int chunkIndex;

    @Column(columnDefinition = "TEXT")
    public String content;
    public int wordCnt;

    public DocumentChunk(UUID uuid, KnowledgeDocument document, int index, String content, int wordCnt) {
        this.id = uuid;
        this.document = document;
        this.chunkIndex = index;
        this.content = content;
        this.wordCnt = wordCnt;
    }
    public DocumentChunk() {}
}
