package com.example.chatapp.web.rest;

import com.example.chatapp.model.dto.MessageDTO;
import com.example.chatapp.service.WebsocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ObjectMapper ob;
    private final WebsocketService websocketService;
    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public void send(String messageDTO) throws JsonProcessingException {
        websocketService.sendMessage((ob.readValue(messageDTO, MessageDTO.class)));
    }
}
