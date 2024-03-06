package com.example.chatapp.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
}
