package com.example.chatapp.service;

import com.example.chatapp.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    void createUser(UserDTO userDTO, MultipartFile image);
}
