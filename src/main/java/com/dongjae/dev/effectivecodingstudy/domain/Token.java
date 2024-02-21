package com.dongjae.dev.effectivecodingstudy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private String username; // 사용자 이름을 기본 키로 사용
    private String token; // 리프레시 토큰 값

    public Token() {
        // JPA는 기본 생성자를 필요로 합니다.
    }

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
