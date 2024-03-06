package com.example.chatapp.service;

import com.example.chatapp.model.dto.MessageDTO;

public interface WebsocketService {
    void sendMessage(MessageDTO message);
}
