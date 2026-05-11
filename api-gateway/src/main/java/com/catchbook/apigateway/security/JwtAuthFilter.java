package com.catchbook.apigateway.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Toujours ajouter les headers CORS
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // Laisser passer les preflight CORS
        if ("OPTIONS".equalsIgnoreCase(method)) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Laisser passer les routes publiques
        if (path.startsWith("/api/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Token manquant");
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Token invalide ou expiré");
            return;
        }

        chain.doFilter(request, response);
    }
}