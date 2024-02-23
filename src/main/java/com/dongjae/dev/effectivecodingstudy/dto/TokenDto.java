package com.dongjae.dev.effectivecodingstudy.dto;

import com.dongjae.dev.effectivecodingstudy.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TokenDto{
    private String username;
    private String token;

    public TokenDto(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public Token toEntity() {
        return new Token(this.username, this.token);
    }
}

