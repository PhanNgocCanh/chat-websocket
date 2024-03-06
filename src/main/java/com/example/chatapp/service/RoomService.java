package com.example.chatapp.service;

import com.example.chatapp.model.dto.RoomDTO;
import com.example.chatapp.model.request.PaginationRequest;
import com.example.chatapp.model.response.PageResponse;
import com.example.chatapp.model.response.RoomResponse;

public interface RoomService {
    void create(RoomDTO roomDTO);

    PageResponse<RoomResponse> getAllRoomIsOpen(PaginationRequest request);
}
