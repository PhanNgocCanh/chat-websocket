package com.example.chatapp.service.impl;

import com.example.chatapp.common.constants.ErrorConstant;
import com.example.chatapp.common.enumeration.RoomType;
import com.example.chatapp.common.exception.CommonException;
import com.example.chatapp.domain.Room;
import com.example.chatapp.domain.User;
import com.example.chatapp.domain.UserRoom;
import com.example.chatapp.mapper.RoomMapper;
import com.example.chatapp.model.dto.RoomDTO;
import com.example.chatapp.model.request.PaginationRequest;
import com.example.chatapp.model.response.PageResponse;
import com.example.chatapp.model.response.RoomResponse;
import com.example.chatapp.repository.RoomRepository;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.repository.UserRoomRepository;
import com.example.chatapp.service.FileService;
import com.example.chatapp.service.RoomService;
import com.example.chatapp.utils.system.PageableUtils;
import com.example.chatapp.utils.system.SecurityUtils;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final FileService fileService;

    public RoomServiceImpl(RoomMapper roomMapper,
                           RoomRepository roomRepository,
                           UserRepository userRepository,
                           UserRoomRepository userRoomRepository,
                           FileService fileService) {
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public void create(RoomDTO roomDTO) {
        try {
            String userId = SecurityUtils.getCurrentUserId();
            if (roomDTO.getUserIds() == null) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.ROOM_NOT_EMPTY);
            }
            Room room = roomMapper.toEntity(roomDTO);
            room.setManagerId(userId);
            if (roomDTO.getUserIds().size() > 1) {
                room.setRoomType(RoomType.GROUP.name());
            } else {
                Optional<User> roommates = userRepository.findById(roomDTO.getUserIds().get(0));
                if (roommates.isPresent()) {
                    room.setRoomType(RoomType.SINGLE.name());
                    room.setImageUrl(roommates.get().getImageUrl());
                } else {
                    throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.USER_NOT_FOUND);
                }
            }
            Room savedRoom = roomRepository.save(room);
            roomDTO.getUserIds().forEach(id -> {
                UserRoom userRoom = new UserRoom();
                userRoom.setUserId(id);
                userRoom.setRoomId(savedRoom.getId());
                userRoom.setIsOpen(true);
                userRoomRepository.save(userRoom);
            });

            UserRoom userManagerRoom = new UserRoom();
            userManagerRoom.setUserId(userId);
            userManagerRoom.setRoomId(savedRoom.getId());
            userManagerRoom.setIsOpen(true);
            userRoomRepository.save(userManagerRoom);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResponse<RoomResponse> getAllRoomIsOpen(PaginationRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageableUtils.of(request.getPage() - 1, request.getSize(), null, true);
        Page<Object[]> rooms = roomRepository.getAllOpenRoom(userId, pageable);
        List<RoomResponse> roomResponses = new ArrayList<>();

        rooms.getContent().forEach(obj -> {
            RoomResponse res = new RoomResponse();
            res.setId((String) obj[0]);
            res.setRoomName((String) obj[1]);
            if (obj[2] != null) {
                res.setImageUrl(fileService.generateUrl((String) obj[2]));

            }
            res.setColor((String) obj[3]);
            res.setLastMessage((String) obj[4]);
            res.setLastMessageDate((Instant) obj[5]);
            roomResponses.add(res);
        });
        return new PageResponse<>(roomResponses, request.getPage(),
                request.getSize(), rooms.getTotalElements());
    }
}
