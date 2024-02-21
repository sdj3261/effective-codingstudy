package com.dongjae.dev.effectivecodingstudy.user.auth;

import com.dongjae.dev.effectivecodingstudy.user.oauth2.UserPrincipal;
import io.jsonwebtoken.*;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RefreshToken {
    private final String token;
    private final Key key;
    private final Instant expiredAt;

    public RefreshToken(UserPrincipal user, Key key) {
        // 리프레시 토큰의 만료 시간 설정 (예: 7일)
        long expirationMillis = 1000 * 60 * 60 * 24 * 30; // 30 days
        Date expiration = new Date(System.currentTimeMillis() + expirationMillis);
        this.expiredAt = expiration.toInstant();

        Map<String, Object> claims = new HashMap<>();
        claims.put("typ", "refresh");
        claims.put("sub", user.getUsername());
        claims.put("iss", "dongjae");

        this.key = key;
        this.token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getToken() {
        return token;
    }

    public Key getKey() {
        return key;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }
}

