package com.catchbook.authservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;
    @Column(length = 512)
    private String token;
    @Column(name = "token_type")
    private String tokenType;
    private String provider;
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "user_id")
    private Long userId;
    private boolean expired;
    private boolean revoked;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
