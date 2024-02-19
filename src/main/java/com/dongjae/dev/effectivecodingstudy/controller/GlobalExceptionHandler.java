package com.dongjae.dev.effectivecodingstudy.controller;

import com.dongjae.dev.effectivecodingstudy.common.ErrorCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public HttpEntity<Map<String, String>> handleUsernameNotFoundException(Exception e){
        Map<String, String> map = new HashMap<>();
        map.put("errorCode", ErrorCode.LOGIN_INVALID.getCode()); // 아이디 틀림
        map.put("msg", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(map);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public HttpEntity<Map<String, String>> handleBadCredentialsException(Exception e){
        Map<String, String> map = new HashMap<>();
        map.put("errorCode", ErrorCode.LOGIN_INVALID.getCode());
        map.put("msg", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(map);
    }
}
