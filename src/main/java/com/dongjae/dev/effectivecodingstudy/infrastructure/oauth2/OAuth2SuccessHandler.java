package com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import com.dongjae.dev.effectivecodingstudy.security.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.security.TokenGenerator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    private final TokenGenerator tokenGenerator;
    private final TokenService tokenService;
    private final String TARGET_URL = "http://localhost:3000/oauth/";

    // 로그인 성공 시 부가작업
    // JWT 발급 후 token과 함께 리다이렉트
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        // access 토큰 생성
        String accessToken = tokenGenerator.generateAccessToken(user.getUserId().toString());
        // Refresh Token DB에 저장 없으면 생성
        String refreshToken = tokenService.getOrCreateRefreshToken(user.getUserId());
        setRefreshTokenCookie(response, refreshToken);

        // Access Token , Refresh Token 프론트 단에 Response Header로 전달
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Refresh-Token", refreshToken);
        getRedirectStrategy().sendRedirect(request, response, TARGET_URL + accessToken);
    }

    private static void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        // Refresh Token을 안전한 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // JS를 통한 접근 방지
        refreshTokenCookie.setPath("/"); // 전체 경로에서 쿠키 사용
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효 기간 설정 (예: 1주일)
        response.addCookie(refreshTokenCookie);
    }
}
