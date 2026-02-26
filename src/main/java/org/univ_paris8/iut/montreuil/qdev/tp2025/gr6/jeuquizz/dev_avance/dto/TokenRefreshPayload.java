package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshPayload {
    @NotBlank(message = "Le refresh token est obligatoire")
    private String refreshToken;

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}