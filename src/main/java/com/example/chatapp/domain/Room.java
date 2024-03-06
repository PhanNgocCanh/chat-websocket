package com.example.chatapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "room")
public class Room extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "room_id")
    private String id;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "color")
    private String color;

    @Column(name = "room_type")
    private String roomType;

}
