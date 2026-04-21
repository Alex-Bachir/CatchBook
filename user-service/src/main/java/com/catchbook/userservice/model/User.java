package com.catchbook.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String pseudo;
    private String email;

    @Column(name = "photo_profile")
    private String photoProfile;

    private String description;
    private String ville;

    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;
}