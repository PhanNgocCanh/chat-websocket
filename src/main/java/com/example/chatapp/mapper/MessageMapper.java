package com.example.chatapp.mapper;

import com.example.chatapp.domain.Message;
import com.example.chatapp.model.dto.MessageRoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageRoomResponse fromModel(Message message);
}
