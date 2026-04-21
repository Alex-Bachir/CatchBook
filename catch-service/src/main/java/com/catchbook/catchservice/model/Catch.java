package com.catchbook.catchservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Catch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catch_id")
    private Long catchId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "fish_name")
    private String fishName;
    @Column(name = "fish_weight")
    private Double fishWeight;
    private Double size;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "catch_date")
    private LocalDateTime catchDate;
    private boolean kept;
    @Column(name = "spot_public")
    private boolean spotPublic;
    private LocalDateTime createdAt;
}
