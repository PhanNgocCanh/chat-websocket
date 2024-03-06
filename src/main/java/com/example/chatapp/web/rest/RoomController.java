package com.example.chatapp.web.rest;

import com.example.chatapp.model.dto.RoomDTO;
import com.example.chatapp.model.request.PaginationRequest;
import com.example.chatapp.model.response.PageResponse;
import com.example.chatapp.model.response.RoomResponse;
import com.example.chatapp.service.RoomService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<PageResponse<RoomResponse>> getAllRoomUser(
            @ParameterObject @Valid PaginationRequest request ) {
        return ResponseEntity.ok(roomService.getAllRoomIsOpen(request));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RoomDTO room) {
        this.roomService.create(room);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
