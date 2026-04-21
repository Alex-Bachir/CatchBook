package com.catchbook.authservice.controller;

import com.catchbook.authservice.model.Token;
import com.catchbook.authservice.service.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/{id}")
    public Token getTokenById(@PathVariable Long id) {
        return tokenService.getTokenById(id);
    }

    @PostMapping
    public Token saveToken(@RequestBody Token token) {
        return tokenService.saveToken(token);
    }

    @PutMapping("/{id}/revoke")
    public void revokeToken(@PathVariable Long id) {
        tokenService.revokeToken(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTokenById(@PathVariable Long id) {
        tokenService.deleteToken(id);
    }

}
