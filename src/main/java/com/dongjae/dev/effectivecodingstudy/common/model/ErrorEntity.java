package com.dongjae.dev.effectivecodingstudy.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorEntity {
    private final String errorCode;
    private final String message;
    private final String details; // Optional, for additional error details if needed

    @Builder
    public ErrorEntity(String errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
