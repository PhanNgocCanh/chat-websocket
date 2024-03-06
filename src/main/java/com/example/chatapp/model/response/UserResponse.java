package com.example.chatapp.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String imageUrl;
}
