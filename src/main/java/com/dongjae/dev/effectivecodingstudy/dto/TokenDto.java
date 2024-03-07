package com.dongjae.dev.effectivecodingstudy.dto;

import com.dongjae.dev.effectivecodingstudy.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TokenDto{
    private String userId;
    private String token;

    public TokenDto(String username, String token) {
        this.userId = username;
        this.token = token;
    }
}

