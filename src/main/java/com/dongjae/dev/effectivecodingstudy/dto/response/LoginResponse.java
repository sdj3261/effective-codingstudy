package com.dongjae.dev.effectivecodingstudy.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String username, String accessToken, String message) {
}
