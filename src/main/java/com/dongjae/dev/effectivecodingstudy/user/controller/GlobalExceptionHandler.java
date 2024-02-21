package com.dongjae.dev.effectivecodingstudy.user.controller;

import com.dongjae.dev.effectivecodingstudy.common.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameNotFoundException(Exception e){
        return ErrorResponse.builder().
                errorCode(ErrorCode.LOGIN_INVALID.getCode()).
                message(e.getMessage()).
                build();
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(Exception e){
        return ErrorResponse.builder().
                errorCode(ErrorCode.LOGIN_INVALID.getCode()).
                message(e.getMessage()).
                build();
    }
}
