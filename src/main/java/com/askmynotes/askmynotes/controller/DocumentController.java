package com.askmynotes.askmynotes.controller;

import com.askmynotes.askmynotes.entities.DocumentDetailResponse;
import com.askmynotes.askmynotes.entities.DocumentResponse;
import com.askmynotes.askmynotes.entities.IndexedDocumentResponse;
import com.askmynotes.askmynotes.enums.IndexingStatus;
import com.askmynotes.askmynotes.sevice.ChunkingService;
import com.askmynotes.askmynotes.sevice.DocumentIndexingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.askmynotes.askmynotes.sevice.DocumentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final ChunkingService chunkingService;
    private final DocumentIndexingService indexingService;

    public DocumentController(DocumentService documentService, ChunkingService chunkingService, DocumentIndexingService indexingService) {
        this.documentService = documentService;
        this.chunkingService = chunkingService;
        this.indexingService = indexingService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestPart("file")MultipartFile file) throws Exception {
        System.out.println("SIDLOG:: received a upload document req");
        DocumentResponse documentResponse = documentService.uploadDocument(file);
        return ResponseEntity.ok().body(documentResponse);
    }

    @GetMapping(value = "/getAllDocuments")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        System.out.println("SIDLOG:: inside get all docs");
        List<DocumentResponse> allDocument = documentService.getAllDocument();
        return ResponseEntity.ok().body(allDocument);
    }

    @GetMapping(value = "/getDocument/{id}")
    public ResponseEntity<DocumentDetailResponse> getDocument(@PathVariable String id) {
        return ResponseEntity.ok().body(
                documentService.getDocument(id)
        );
    }

    @PostMapping("/{id}/chunks")
    public List<ChunkingService.DocumentChunkResponse> getChunks(@PathVariable UUID id) {
        return chunkingService.getChunksForDoc(id);
    }

    @PostMapping("/{id}/indexDocument")
    public IndexedDocumentResponse indexDocument(@PathVariable UUID id) {
        int cnt = indexingService.indexDocument(id);
        return new IndexedDocumentResponse(
                id,
                cnt,
                IndexingStatus.INDEXED.toString()
        );
    }
}
