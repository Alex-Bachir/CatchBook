package com.catchbook.authservice.service;

import com.catchbook.authservice.model.Token;
import com.catchbook.authservice.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // Récupérer un token par son id
    public Token getTokenById(Long id) {
        return tokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    // Sauvegarder un token
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    // Révoquer un token
    public void revokeToken(Long id) {
        Token token = getTokenById(id);
        token.setRevoked(true);
        tokenRepository.save(token);
    }

    // Supprimer un token
    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
}
