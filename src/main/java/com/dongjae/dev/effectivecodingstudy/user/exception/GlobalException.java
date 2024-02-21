package com.dongjae.dev.effectivecodingstudy.user.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final String errorCode;

    public GlobalException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
