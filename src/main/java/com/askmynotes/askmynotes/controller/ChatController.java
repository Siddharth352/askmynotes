package com.askmynotes.askmynotes.controller;

import com.askmynotes.askmynotes.entities.ChatRequest;
import com.askmynotes.askmynotes.entities.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.askmynotes.askmynotes.sevice.ChatService;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/askQuestion")
    public ResponseEntity<String> askQuestion(@RequestBody ChatRequest chatRequest) {
        ChatResponse chatResponse = chatService.answerQuestion(chatRequest);
        return ResponseEntity
                .ok().body(chatResponse.toString());
    }
}
