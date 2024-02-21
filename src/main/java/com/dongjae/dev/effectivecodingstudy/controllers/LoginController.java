package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.AuthResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import com.dongjae.dev.effectivecodingstudy.utils.AccessTokenGenerator;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Collections;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenGenerator accessTokenGenerator;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
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
        String accessToken = accessTokenGenerator.generateAccessToken(userPrincipal.getUserId().toString());
        return BaseResponse.success(LoginResponse.builder().
                username(user.getUsername()).
                accessToken(accessToken).
                message("로그인 성공")
                .build());
    }

    // /auth/** 모양의 주소는 모두 인증 필요
    @GetMapping("/auth/test")
    public AuthResponse authTest(@AuthenticationPrincipal UserPrincipal user){
        return AuthResponse.builder().
                authTest(user).
                build();
    }
}
