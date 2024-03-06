package com.example.chatapp.repository;

import com.example.chatapp.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, String> {
}
