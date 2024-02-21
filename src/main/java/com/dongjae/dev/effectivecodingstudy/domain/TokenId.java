package com.dongjae.dev.effectivecodingstudy.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class TokenId extends EntityId{
    public TokenId(String value) {
        super(value);
    }

    public TokenId() {
        super();
    }

    public static TokenId generate() {
        return new TokenId(newTsid());
    }

    public static TokenId of(String tokenId) {
        return new TokenId(tokenId);
    }
}
