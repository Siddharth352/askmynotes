package com.askmynotes.askmynotes.entities;

public record SemanticSearchRequest (
        int k,
        String question
) {}
