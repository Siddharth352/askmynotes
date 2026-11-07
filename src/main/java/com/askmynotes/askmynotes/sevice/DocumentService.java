package com.askmynotes.askmynotes.sevice;

import com.askmynotes.askmynotes.entities.DocumentChunk;
import com.askmynotes.askmynotes.entities.DocumentDetailResponse;
import com.askmynotes.askmynotes.entities.DocumentResponse;
import com.askmynotes.askmynotes.entities.KnowledgeDocument;
import com.askmynotes.askmynotes.enums.IndexingStatus;
import com.askmynotes.askmynotes.repository.DocumentChunkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.askmynotes.askmynotes.repository.DocumentRepository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ChunkingService chunkingService;
    private final DocumentChunkRepository documentChunkRepository;

    public DocumentService(DocumentRepository documentRepository, ChunkingService chunkingService, DocumentChunkRepository documentChunkRepository) {
        this.documentRepository = documentRepository;
        this.chunkingService = chunkingService;
        this.documentChunkRepository = documentChunkRepository;
    }

    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file) throws Exception {
        KnowledgeDocument knowledgeDocument = extractDocumentFromFile(file);
        knowledgeDocument.setIndexingStatus(IndexingStatus.CHUNKED);
        System.out.println("SIDLOG:: got the knowledge document = " + knowledgeDocument);
        KnowledgeDocument savedDocument = documentRepository.save(knowledgeDocument);
        List<DocumentChunk> chunks = chunkingService.split(knowledgeDocument.content)
                .stream()
                .map(chunk -> new DocumentChunk(
                        UUID.randomUUID(),
                        savedDocument,
                        chunk.index(),
                        chunk.content(),
                        chunk.wordCount()
                ))
                .toList();
        documentChunkRepository.saveAll(chunks);

        System.out.println("SIDLOG:: uplaoded docment successfully with id = " + savedDocument.id);

        return new DocumentResponse(
                knowledgeDocument.id,
                knowledgeDocument.sizeByte,
                knowledgeDocument.docName,
                knowledgeDocument.createdAt
        );
    }

    public List<DocumentResponse> getAllDocument() {
        List<DocumentResponse> docLists = new ArrayList<>();
        return docLists;
    }

    private KnowledgeDocument extractDocumentFromFile(MultipartFile file) throws Exception {
        String fileName = extractFilename(file);
        byte[] bytes = readBytes(file);
        String content = decodeUtf8(bytes);

        return new KnowledgeDocument(
                UUID.randomUUID(),
                file.getSize(),
                fileName,
                content
        );
    }

    private String extractFilename(MultipartFile file) {
        String cleanedPath = StringUtils.cleanPath(
                Optional.ofNullable(file.getOriginalFilename())
                        .orElse("")
        );

        return StringUtils.getFilename(cleanedPath);
    }

    private byte[] readBytes(MultipartFile file) throws Exception {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new Exception("Some error while reading file");
        }
    }

    private String decodeUtf8(byte[] bytes) throws Exception {
        try {
            return StandardCharsets.UTF_8
                    .newDecoder()
                    .decode(ByteBuffer.wrap(bytes))
                    .toString();
        } catch (Exception e) {
            throw new Exception("Error while decoding the data");
        }
    }

    public DocumentDetailResponse getDocument(String docId) {
        return new DocumentDetailResponse();
    }
}
