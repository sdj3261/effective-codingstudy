package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.application.user.LoginService;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.request.RefreshTokenRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.AuthResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.RefreshTokenResponse;
import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public BaseResponse<RefreshTokenResponse> refreshAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        RefreshTokenResponse refreshTokenResponse = tokenService.refreshAccessToken(refreshToken);
        return BaseResponse.success(refreshTokenResponse);
    }


    // /auth/** 모양의 주소는 모두 인증 필요
    @GetMapping("/auth/test")
    public AuthResponse authTest(@AuthenticationPrincipal UserPrincipal user){
        return AuthResponse.builder().
                name(user.getName()).
                username(user.getUsername()).
                userId(user.getUserId().toString()).
                build();
    }
}
