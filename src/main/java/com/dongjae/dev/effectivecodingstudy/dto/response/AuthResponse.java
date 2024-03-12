package com.dongjae.dev.effectivecodingstudy.dto.response;
import lombok.Builder;

@Builder
public record AuthResponse(String userId, String email, String name)  {
}
