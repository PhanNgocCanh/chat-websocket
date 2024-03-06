package com.example.chatapp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RoomResponse {
    private String id;
    private String roomName;
    private String imageUrl;
    private String color;
    private String lastMessage;
    private Instant lastMessageDate;
}
