package com.example.chatapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "document")
public class Document extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "document_id")
    private String id;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_type")
    private String documentType;
}
