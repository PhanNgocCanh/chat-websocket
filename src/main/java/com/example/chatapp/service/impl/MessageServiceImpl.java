package com.example.chatapp.service.impl;

import com.example.chatapp.domain.Message;
import com.example.chatapp.mapper.MessageMapper;
import com.example.chatapp.model.dto.MessageDTO;
import com.example.chatapp.model.dto.MessageRoomResponse;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.service.FileService;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.utils.system.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final FileService fileService;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageRoomResponse> getMessage(String roomId) {
        String userId = SecurityUtils.getCurrentUserId();
        List<Object[]> messages = messageRepository.getMessageInRoom(roomId);
        List<MessageRoomResponse> responses = messages.stream().map(MessageRoomResponse::fromModel).toList();
        responses.forEach(res -> {
            res.getUserInfo().setImageUrl(fileService.generateUrl(res.getUserInfo().getImageUrl()));
        });
        return responses;
    }

    @Override
    public void saveMessage(MessageDTO message) {
        String userId = message.getUserId();
        Message m = new Message();
        m.setUserId(userId);
        m.setContent(message.getContent());
        m.setRoomId(message.getRoomId());
        messageRepository.save(m);
    }
}
