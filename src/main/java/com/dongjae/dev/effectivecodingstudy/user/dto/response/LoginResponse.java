package com.dongjae.dev.effectivecodingstudy.user.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String username, String accessToken, String message) {
}
