package com.example.chatapp.service.impl;

import com.example.chatapp.common.constants.ErrorConstant;
import com.example.chatapp.common.exception.CommonException;
import com.example.chatapp.domain.User;
import com.example.chatapp.model.dto.UserDTO;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.AuthService;
import com.example.chatapp.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthServiceImpl implements AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String USER_FOLDER = "user_profile_image";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileService fileService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           FileService fileService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    @Override
    public void createUser(UserDTO userDTO, MultipartFile image) {
        try {
            User user = new User();
            user.setUserName(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String imagePath = fileService.uploadFile(USER_FOLDER, image);
            user.setImageUrl(imagePath);
            userRepository.save(user);
        } catch (CommonException e) {
            log.error("Error while create user. Message: " + e.getMessage());
            throw CommonException.create(e.getHttpStatus()).code(e.getCode());
        } catch (Exception e) {
            log.error("Error while create user. Message: " + e.getMessage());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
    }
}
