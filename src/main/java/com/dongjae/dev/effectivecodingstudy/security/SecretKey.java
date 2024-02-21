package com.dongjae.dev.effectivecodingstudy.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

// JWT Signature에 쓸 Key 생성용 객체
@Component
public class SecretKey {
    // yml 변수 들고오기
    @Value("${secret}")
    private String secret;
    @Getter
    private Key key;

    @PostConstruct // 의존성 주입 후 초기화
    public void init() {
        // base64를 byte[]로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        // byte[]로 Key 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
}
