package com.catchbook.authservice.controller;

import com.catchbook.authservice.model.User;
import com.catchbook.authservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of("token", "fake-jwt-token-" + email));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "Email already exists"));
        }

        // 1. Sauvegarde dans auth-service
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        // 2. Crée le profil dans user-service
        try {
            Map<String, String> userProfile = Map.of(
                    "email", email,
                    "pseudo", email.split("@")[0],
                    "firstName", "",
                    "lastName", ""
            );
            restTemplate.postForObject(
                    "http://user-service:8082/api/users",
                    userProfile,
                    Map.class
            );
        } catch (Exception e) {
            System.out.println("Erreur création profil user-service : " + e.getMessage());
        }

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}