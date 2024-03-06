package com.example.chatapp.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
@Data
@Builder
public class MessageRoomResponse {
    private String id;
    private String content;
    private UserInfo userInfo;
    private Instant createdDate;

    public static MessageRoomResponse fromModel(Object[] objects) {
        UserInfo uf = new UserInfo();
        uf.setUserId(objects[3].toString());
        uf.setUsername(objects[4].toString());
        uf.setImageUrl(objects[5].toString());
        return MessageRoomResponse.builder()
                .id(objects[0].toString())
                .content(objects[1].toString())
                .createdDate(Instant.parse(objects[2].toString()))
                .userInfo(uf)
                .build();
    }
}
