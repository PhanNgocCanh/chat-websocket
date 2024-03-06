package com.example.chatapp.service;

import com.example.chatapp.model.dto.ProfileDTO;
import com.example.chatapp.model.dto.UserDTO;
import com.example.chatapp.model.response.UserResponse;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();

    ProfileDTO getMyProfile();
}
