package com.example.chatapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private String id;
    private String roomName;
    private String color;
    private List<String> userIds;
}
