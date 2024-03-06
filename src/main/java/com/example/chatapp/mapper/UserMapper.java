package com.example.chatapp.mapper;

import com.example.chatapp.domain.User;
import com.example.chatapp.model.dto.UserDTO;
import com.example.chatapp.model.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{
    UserResponse toUserResponse(User user);
}
