package com.dongjae.dev.effectivecodingstudy.dto.response;

import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import lombok.Builder;

@Builder
public record AuthResponse(UserPrincipal authTest)  {
}
