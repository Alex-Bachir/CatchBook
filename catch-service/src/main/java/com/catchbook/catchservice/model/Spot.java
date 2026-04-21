package com.catchbook.catchservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long spotId;
    // Une prise (Catch) peut avoir un seul spot
    //Un spot appartient à une seule prise
    @OneToOne
    @JoinColumn(name = "catch_id")
    private Catch fishcatch;

    @Column(name = "user_id")
    private Long userId;

    private Double latitude;
    private Double longitude;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}