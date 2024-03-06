package com.example.chatapp.service.impl;

import com.example.chatapp.common.exception.CommonException;
import com.example.chatapp.domain.User;
import com.example.chatapp.mapper.UserMapper;
import com.example.chatapp.model.dto.ProfileDTO;
import com.example.chatapp.model.response.UserResponse;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.FileService;
import com.example.chatapp.service.UserService;
import com.example.chatapp.utils.system.SecurityUtils;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final FileService fileService;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper,
                           FileService fileService,
                           UserRepository userRepository) {
        this.userMapper = userMapper;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUser() {
        try {
            String userId = SecurityUtils.getCurrentUserId();
            List<User> users = userRepository.getAllUser(userId);
            List<UserResponse> userResponses = users.stream()
                    .map(userMapper::toUserResponse).toList();
            userResponses.forEach(res -> {
                res.setImageUrl(fileService.generateUrl(res.getImageUrl()));
            });
            return userResponses;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ProfileDTO getMyProfile() {
        String userId = SecurityUtils.getCurrentUserId();
        Optional<User> userOtp = userRepository.findById(userId);
        if (userOtp.isEmpty()) {
            throw new CommonException().code("user.not_found");
        }
        User user = userOtp.get();
        ProfileDTO profile = new ProfileDTO();
        profile.setId(userId);
        profile.setUsername(user.getUserName());
        profile.setEmail(user.getEmail());
        profile.setImageUrl(fileService.generateUrl(user.getImageUrl()));
        return profile;
    }
}
