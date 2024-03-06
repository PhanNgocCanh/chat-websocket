package com.example.chatapp.repository;

import com.example.chatapp.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("select m.id, m.content, m.createdDate, u.id, u.userName, u.imageUrl " +
            "from Message m inner join User u " +
            "on m.userId = u.id " +
            "where m.roomId = :roomId")
    List<Object[]> getMessageInRoom(@Param("roomId") String roomId);
}
