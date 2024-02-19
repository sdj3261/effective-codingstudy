package com.dongjae.dev.effectivecodingstudy.controller;

import com.dongjae.dev.effectivecodingstudy.common.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleUsernameNotFoundException(Exception e){
        return ErrorEntity.builder().
                errorCode(ErrorCode.LOGIN_INVALID.getCode()).
                message(e.getMessage()).
                build();
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorEntity handleBadCredentialsException(Exception e){
        return ErrorEntity.builder().
                errorCode(ErrorCode.LOGIN_INVALID.getCode()).
                message(e.getMessage()).
                build();
    }
}
