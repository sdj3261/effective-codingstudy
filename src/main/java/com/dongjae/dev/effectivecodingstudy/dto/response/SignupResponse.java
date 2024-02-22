package com.dongjae.dev.effectivecodingstudy.dto.response;

import lombok.Builder;

@Builder
public record SignupResponse(String username, String accessToken) {
}
