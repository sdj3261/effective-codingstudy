package com.dongjae.dev.effectivecodingstudy.oauth2;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import com.dongjae.dev.effectivecodingstudy.utils.AccessTokenGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AccessTokenGenerator accessTokenGenerator;
    private final TokenService tokenService;
    private final String TARGET_URL = "http://localhost:3000/oauth/";

    // 로그인 성공 시 부가작업
    // JWT 발급 후 token과 함께 리다이렉트
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        // access 토큰 생성
        String accessToken = accessTokenGenerator.generateAccessToken(user.getUserId().toString());
        String refreshToken = accessTokenGenerator.generateRefreshToken(user.getUserId().toString());
        // Refresh Token DB에 저장
        tokenService.updateOrInsertRefreshToken(user.getUsername(), refreshToken);

        // Access Token , Refresh Token 프론트 단에 Response Header로 전달
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        getRedirectStrategy().sendRedirect(request, response, TARGET_URL + accessToken);
    }
}
