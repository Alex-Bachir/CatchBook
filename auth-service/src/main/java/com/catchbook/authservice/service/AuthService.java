package com.catchbook.authservice.service;

import com.catchbook.authservice.dto.AuthResponse;
import com.catchbook.authservice.dto.LoginRequest;
import com.catchbook.authservice.dto.RegisterRequest;
import com.catchbook.authservice.model.User;
import com.catchbook.authservice.repository.UserRepository;
import com.catchbook.authservice.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getEmail(), user.getUserId());
            return new AuthResponse(token, user.getEmail(), user.getUserId());
        }

        throw new RuntimeException("Invalid credentials");
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        try {
            Map<String, String> userProfile = Map.of(
                    "email", request.getEmail(),
                    "pseudo", request.getEmail().split("@")[0],
                    "firstName", "",
                    "lastName", ""
            );
            restTemplate.postForObject("http://user-service:8082/api/users", userProfile, Map.class);
        } catch (Exception e) {
            System.out.println("Erreur création profil user-service : " + e.getMessage());
        }
    }
}