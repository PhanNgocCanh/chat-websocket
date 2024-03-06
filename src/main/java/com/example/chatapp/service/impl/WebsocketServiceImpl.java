package com.example.chatapp.service.impl;

import com.example.chatapp.model.dto.MessageDTO;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public void sendMessage(MessageDTO message) {
        messageService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/message/" + message.getRoomId() , message);
    }
}
