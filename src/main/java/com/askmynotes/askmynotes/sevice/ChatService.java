package com.askmynotes.askmynotes.sevice;

import com.askmynotes.askmynotes.entities.ChatRequest;
import com.askmynotes.askmynotes.entities.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public ChatResponse answerQuestion(ChatRequest chatRequest) {
        return new ChatResponse();
    }
}
