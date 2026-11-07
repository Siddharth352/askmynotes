package com.askmynotes.askmynotes.repository;

import com.askmynotes.askmynotes.entities.KnowledgeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<KnowledgeDocument, UUID> {
}
