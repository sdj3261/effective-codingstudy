package com.dongjae.dev.effectivecodingstudy.auth;

import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// 로그인 이후 access token 검증하는 필터
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;

    private String resolveToken(HttpServletRequest request) {
        // Authorization
        String token = request.getHeader(AUTHORIZATION_HEADER);

        // 공백 혹은 null이 아니고 Bearer로 시작하면
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            // Bearer 떼고 줌
            return token.split(" ")[1].trim();
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("*** JWT FILTER 작동 *** 주소: {}", request.getRequestURL());

        String token = resolveToken(request);

        // 디코딩할만한 토큰이 왔으면 인증시작
        if (token != null) {
            // header의 token로 token, key를 포함하는 새로운 JwtAuthToken 만들기
            AccessToken accessToken = new AccessToken(token, secretKey.getKey());
            //토큰에서 유저이름 가져오기
            Claims claims = accessToken.getData();
            UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(claims.getSubject());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority("USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("user pk: {} 인증 성공!", claims.getSubject());
        }

        filterChain.doFilter(request, response);
    }
}
