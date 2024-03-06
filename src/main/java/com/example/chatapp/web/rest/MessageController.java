package com.example.chatapp.web.rest;

import com.example.chatapp.model.dto.MessageRoomResponse;
import com.example.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public List<MessageRoomResponse> getAllMessage(@RequestParam("roomId") String roomId) {
        return messageService.getMessage(roomId);
    }
}
