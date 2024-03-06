package com.example.chatapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "message")
public class Message extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "message_id")
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "content")
    private String content;
}
