package com.dongjae.dev.effectivecodingstudy.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String message;
    private final String details; // Optional, for additional error details if needed

    @Builder
    public ErrorResponse(String errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
