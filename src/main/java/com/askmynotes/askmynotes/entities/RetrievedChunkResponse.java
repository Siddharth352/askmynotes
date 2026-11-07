package com.askmynotes.askmynotes.entities;

import java.util.Map;

public record RetrievedChunkResponse (
        String docId,
        String content,
        Map<String, Object> metadata

) {}
