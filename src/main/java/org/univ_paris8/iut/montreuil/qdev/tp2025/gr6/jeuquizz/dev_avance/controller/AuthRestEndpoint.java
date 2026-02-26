package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AuthRequest;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AuthResponse;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security.AppUserDetails;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security.JWTTokenProvider;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Endpoints d'authentification JWT")
public class AuthRestEndpoint {

    private final AuthenticationManager authManager;
    private final JWTTokenProvider tokenProvider;

    public AuthRestEndpoint(AuthenticationManager authManager, JWTTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        AppUserDetails principal = (AppUserDetails) authentication.getPrincipal();
        String role = principal.getAuthorities().iterator().next().getAuthority();

        String accessToken = tokenProvider.generateAccessToken(principal.getUserId(), principal.getUsername(), role);
        String refreshToken = tokenProvider.generateRefreshToken(principal.getUserId(), principal.getUsername(), role);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, tokenProvider.getExpirationMs()));
    }
}