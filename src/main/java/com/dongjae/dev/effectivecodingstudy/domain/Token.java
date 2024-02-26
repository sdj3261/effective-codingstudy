package com.dongjae.dev.effectivecodingstudy.domain;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.InvalidTokenException;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {
    @Id
    private String username;
    private String token;

    public Token() {
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

    public void updateToken(String token) {
        this.token = token;
    }

    public void validateSameToken(String token) {
        if (!this.token.equals(token)) {
            throw new InvalidTokenException("Token is Invalid");
        }
    }
}
