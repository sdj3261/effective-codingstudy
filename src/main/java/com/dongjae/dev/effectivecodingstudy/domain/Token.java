package com.dongjae.dev.effectivecodingstudy.domain;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.InvalidTokenException;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {
    @EmbeddedId
    private UserId userId;
    @Getter
    private String token;

    public Token() {
    }

    public Token(UserId userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }

}
