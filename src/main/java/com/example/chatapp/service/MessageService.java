package com.example.chatapp.service;

import com.example.chatapp.model.dto.MessageDTO;
import com.example.chatapp.model.dto.MessageRoomResponse;

import java.util.List;

public interface MessageService {
    List<MessageRoomResponse> getMessage(String roomId);

    void saveMessage(MessageDTO message);
}
