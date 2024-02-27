package com.dongjae.dev.effectivecodingstudy.common.model;

import lombok.Builder;
import lombok.Getter;

/**
 * @param details Optional, for additional error details if needed
 */
public record ErrorResponse(String errorCode, String message, String details) {
    @Builder
    public ErrorResponse {
    }
}
