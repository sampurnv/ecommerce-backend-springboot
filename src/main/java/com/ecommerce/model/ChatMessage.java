package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    
    private String id;
    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
    
    public enum MessageType {
        CHAT, JOIN, LEAVE, TYPING
    }
    
    public ChatMessage(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
}