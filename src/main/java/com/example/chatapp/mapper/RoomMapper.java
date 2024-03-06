package com.example.chatapp.mapper;

import com.example.chatapp.domain.Room;
import com.example.chatapp.model.dto.RoomDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {
}
