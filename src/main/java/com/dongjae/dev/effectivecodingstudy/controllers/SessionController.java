package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.application.user.LoginService;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.AuthResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.TokenResponse;
import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SessionController {
    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return BaseResponse.success(loginService.login(request));
    }

    @PostMapping("/refresh")
    public BaseResponse<TokenResponse> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Refresh-Token");
        TokenResponse tokenResponse = tokenService.refreshAccessToken(refreshToken);
        setRefreshTokenCookie(response, tokenResponse.refreshToken());
        return BaseResponse.success(tokenResponse);
    }


    // /auth/** 모양의 주소는 모두 인증 필요
    @GetMapping("/auth/test")
    public BaseResponse<AuthResponse> authTest(@AuthenticationPrincipal UserPrincipal user){
        return BaseResponse.success(AuthResponse.builder().
                name(user.getName()).
                username(user.getUsername()).
                userId(user.getUserId().toString()).
                build());
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
