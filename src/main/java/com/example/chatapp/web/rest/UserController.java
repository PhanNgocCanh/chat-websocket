package com.example.chatapp.web.rest;

import com.example.chatapp.model.dto.ProfileDTO;
import com.example.chatapp.model.response.UserResponse;
import com.example.chatapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUser() {
        return this.userService.getAllUser();
    }

    @GetMapping("/profile/me")
    public ProfileDTO getMyProfile() {
        return userService.getMyProfile();
    }
}
