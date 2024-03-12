package com.dongjae.dev.effectivecodingstudy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenGenerator {
    private final Algorithm algorithm;
    private static final int ACCESS_TOKEN_EXPIRY_HOURS = 1; // 엑세스 토큰 유효 시간
    private static final int REFRESH_TOKEN_EXPIRY_DAYS = 7; // 리프레시 토큰 유효 시간

    public TokenGenerator(@Value("${secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(Instant.now().plus(ACCESS_TOKEN_EXPIRY_HOURS, ChronoUnit.HOURS)))
                .sign(algorithm);
    }

    public String generateRefreshToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(Instant.now().plus(REFRESH_TOKEN_EXPIRY_DAYS, ChronoUnit.DAYS)))
                .sign(algorithm);
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return false;
        } catch (JWTVerificationException e) {
            return true;
        }
    }

    public String getUserIdFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
