package com.catchbook.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Pour l'instant on simule — on implémentera le vrai login plus tard
        if (email != null && password != null) {
            return ResponseEntity.ok(Map.of("token", "fake-jwt-token"));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}