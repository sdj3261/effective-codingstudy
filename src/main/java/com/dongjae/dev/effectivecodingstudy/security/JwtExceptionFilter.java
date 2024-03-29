package com.dongjae.dev.effectivecodingstudy.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorResponse;
import com.dongjae.dev.effectivecodingstudy.utils.ErrorResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 인증 관련 Exception 처리를 위한 필터입니다
// 필터를 두 개 만들기 싫다면, JwtFilter의 doFilterInternal 코드를 try-catch로 감아줘도 됩니다.

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            setErrorResponse(response, "ACCESS_TOKEN_EXPIRED");
        } catch (JWTVerificationException e) {
            log.debug("JWT EXCEPTION FILTER");
            setErrorResponse(response, "INVALID JWT TOKEN");
        } catch (JwtException | SecurityException e){
            setErrorResponse(response, "[JWt Exception] CANNOT LOGIN");
        }
    }

    public void setErrorResponse(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        BaseResponse<ErrorResponse> baseResponse = ErrorResponseUtil.errorResponse(msg,ErrorCode.TOKEN_ERROR);
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}
