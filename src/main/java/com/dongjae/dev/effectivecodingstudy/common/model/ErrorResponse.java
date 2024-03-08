package com.dongjae.dev.effectivecodingstudy.common.model;

import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;

/**
 * @param details Optional, for additional error details if needed
 */
public record ErrorResponse(ErrorCode errorCode, String message, String details) {
    @Builder
    public ErrorResponse {
    }
}
