package com.dongjae.dev.effectivecodingstudy.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserId extends EntityId{
    public UserId(String value) {
        super(value);
    }

    public UserId() {
        super();
    }

    public static UserId generate() {
        return new UserId(newTsid());
    }

    public static UserId of(String userId) {
        return new UserId(userId);
    }
}
