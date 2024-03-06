package com.example.chatapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String userId;
    private String username;
    private String imageUrl;
    private String roomId;
    private String content;
    private Instant date;
}
