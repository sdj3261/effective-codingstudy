package com.dongjae.dev.effectivecodingstudy.auth;

import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class AccessToken {
    // 만료 시간(300분 후)
    public static final int EXPIRED_AFTER = 1440;

    // 암호화된 token
    private final String token;

    // key
    private final Key key;

    // 만료 일자
    private Instant expiredAt;

    // AccessToken 생성자
    // UserPrincipal로 만들도록 수정
    public AccessToken(UserPrincipal user, Key key) {
        Instant expiredDate = Instant.now().plus(24, ChronoUnit.HOURS);

        //서명
        Map<String, String> claims = new HashMap<>();

        claims.put("iss", "dongjae"); // 발행인
        claims.put("aud", user.getUsername()); // 토큰 대상자(User PK)
        claims.put("exp", LocalDateTime.now().toString()); // 발행 시간

        this.key = key;
        this.expiredAt = expiredAt;
        this.token = createJwtAuthToken(user.getUsername(), claims, expiredDate).get();
    }

    //JWT 적용
    public Optional<String> createJwtAuthToken(String username, Map<String, String> claimMap, Date expiredDate) {
        return Optional.ofNullable(Jwts.builder()
                .setSubject(username)
                .addClaims(new DefaultClaims(claimMap))
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact()
        );
    }

    public AccessToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    // JWT 디코딩
    public Claims getData() throws ExpiredJwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
