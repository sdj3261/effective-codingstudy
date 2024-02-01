package com.dongjae.dev.effectivecodingstudy.config;

import com.dongjae.dev.effectivecodingstudy.auth.JwtExceptionFilter;
import com.dongjae.dev.effectivecodingstudy.auth.JwtFilter;
import com.dongjae.dev.effectivecodingstudy.oauth2.OAuth2MemberService;
import com.dongjae.dev.effectivecodingstudy.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2MemberService oAuth2MemberService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // MEMIL CORS 설정 메소드
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));

            // MEMIL React가 다른 포트를 사용한다면 아래 코드를 수정하세요
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
            config.setAllowCredentials(true);

            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(HttpBasicConfigurer::disable) // MEMIL: 로그인창
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/login").permitAll()

                                .requestMatchers("/auth/**").authenticated()
                )
                .oauth2Login(oauth2Configurer -> oauth2Configurer
                        .successHandler(successHandler)
                        .userInfoEndpoint(configurer -> configurer.userService(oAuth2MemberService))
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtFilter.class);

        return httpSecurity.build();
    }
}
