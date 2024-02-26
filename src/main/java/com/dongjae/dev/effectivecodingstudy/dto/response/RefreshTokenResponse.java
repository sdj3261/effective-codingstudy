package com.dongjae.dev.effectivecodingstudy.dto.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponse (String accessToken, String refreshToken) {
}
