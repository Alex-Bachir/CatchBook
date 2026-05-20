package com.catchbook.authservice.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.user-service.url}")
    private String userServiceUrl;

    public OAuth2SuccessHandler(JwtUtil jwtUtil, RestTemplate restTemplate) {
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String googleId = oauth2User.getAttribute("sub");
        String email    = oauth2User.getAttribute("email");
        String name     = oauth2User.getAttribute("name");
        String picture  = oauth2User.getAttribute("picture");

        Map<String, String> body = new HashMap<>();
        body.put("googleId", googleId);
        body.put("email", email);
        body.put("name", name);
        body.put("picture", picture);

        Map<String, Object> user = restTemplate.postForObject(
                userServiceUrl + "/api/users/oauth2",
                body,
                Map.class
        );

        Long userId = Long.valueOf(user.get("userId").toString());
        String token = jwtUtil.generateToken(email, userId);

        response.sendRedirect(frontendUrl + "/auth/callback?token=" + token);
    }
}