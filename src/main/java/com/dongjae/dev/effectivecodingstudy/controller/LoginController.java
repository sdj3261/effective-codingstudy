package com.dongjae.dev.effectivecodingstudy.controller;

import com.dongjae.dev.effectivecodingstudy.auth.AccessToken;
import com.dongjae.dev.effectivecodingstudy.auth.SecretKey;
import com.dongjae.dev.effectivecodingstudy.common.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorEntity;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.AuthResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.entity.User;
import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username()).
                orElseThrow(() -> new UsernameNotFoundException("WRONG USERNAME"));

        // 비밀번호 일치 여부 확인
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("WRONG PASSWORD");
        }

        //Security Context Holder에 저장
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPrincipal, request.password(), Collections.singleton(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(token);

        //서버 비밀키로 userPrincipal 객체를 통해 엑세스토큰 만들기
        String accessToken = new AccessToken(userPrincipal, secretKey.getKey()).getToken();
        return LoginResponse.builder().
                username(user.getUsername()).
                accessToken(accessToken).
                message("로그인 성공")
                .build();
    }

    // /auth/** 모양의 주소는 모두 인증 필요
    @GetMapping("/auth/test")
    public AuthResponse authTest(@AuthenticationPrincipal UserPrincipal user){
        return AuthResponse.builder().
                authTest(user).
                build();
    }
}
