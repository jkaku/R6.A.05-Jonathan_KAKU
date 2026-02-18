package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenService {
    private static final Map<String, User> tokenStore = new HashMap<>();

    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user);
        return token;
    }

    public User validateToken(String token) {
        return tokenStore.get(token);
    }
}