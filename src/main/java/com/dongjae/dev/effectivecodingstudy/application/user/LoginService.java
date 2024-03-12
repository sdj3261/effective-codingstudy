package com.dongjae.dev.effectivecodingstudy.application.user;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.EmailNotFoundException;
import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.security.UserPrincipal;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import com.dongjae.dev.effectivecodingstudy.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.username())
                .orElseThrow(() -> new EmailNotFoundException("WRONG EMAIL"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("WRONG PASSWORD");
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPrincipal, request.password(), Collections.singleton(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
        //로그인 성공시 엑세스 토큰과 이름 발급
        String accessToken = tokenGenerator.generateAccessToken(userPrincipal.getUserId().toString());
        return LoginResponse.builder()
                .email(user.getEmail())
                .accessToken(accessToken)
                .message("로그인 성공")
                .build();
    }
}
