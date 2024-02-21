package com.dongjae.dev.effectivecodingstudy.application.exceptions;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final String errorCode;

    public GlobalException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
