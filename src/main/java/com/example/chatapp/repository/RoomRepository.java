package com.example.chatapp.repository;

import com.example.chatapp.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, String> {
    @Query(value = "SELECT r.room_id, r.room_name, r.image_url, r.color, m.content, m.created_date " +
            "FROM Room r INNER JOIN user_room ur ON r.room_id = ur.room_id " +
            "LEFT JOIN Message m ON r.room_id = m.room_id AND m.message_id IN " +
            "(SELECT me.message_id FROM Message me ORDER BY me.created_date DESC FETCH FIRST 1 ROWS ONLY ) " +
            "WHERE ur.user_id = :userId " ,nativeQuery = true)
    Page<Object[]> getAllOpenRoom(@Param("userId") String userId,
                                  Pageable pageable);
}
