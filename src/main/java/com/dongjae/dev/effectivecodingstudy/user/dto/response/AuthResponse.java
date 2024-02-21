package com.dongjae.dev.effectivecodingstudy.user.dto.response;

import com.dongjae.dev.effectivecodingstudy.user.oauth2.UserPrincipal;
import lombok.Builder;

@Builder
public record AuthResponse(UserPrincipal authTest)  {
}
