package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTTokenProvider {

    @Value("${api.security.jwt.secret}")
    private String secretKey;

    @Value("${api.security.jwt.expiration-time}")
    private long expirationMs;

    private final long refreshExpirationMs = 86400000L * 7;

    public String generateAccessToken(Long userId, String username, String role) {
        return buildToken(userId, username, role, expirationMs, "access");
    }

    public String generateRefreshToken(Long userId, String username, String role) {
        return buildToken(userId, username, role, refreshExpirationMs, "refresh");
    }

    private String buildToken(Long userId, String username, String role, long expiration, String tokenType) {
        return Jwts.builder()
                .subject(username)
                .claims(Map.of("userId", userId, "role", role, "type", tokenType))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    public String extractTokenType(String token) { return extractClaim(token, claims -> claims.get("type", String.class)); }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isRefreshToken(String token) { return "refresh".equals(extractTokenType(token)); }
    public long getExpirationMs() { return expirationMs; }

    private boolean isTokenExpired(String token) { return extractClaim(token, Claims::getExpiration).before(new Date()); }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return resolver.apply(claims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}