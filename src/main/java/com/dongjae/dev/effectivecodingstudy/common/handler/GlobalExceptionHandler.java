package com.dongjae.dev.effectivecodingstudy.common.handler;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.ConvertException;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.EmailNotFoundException;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.ProviderNotFoundException;
import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, EmailNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameNotFoundException(Exception e){
        return ErrorResponse.builder().
                errorCode(ErrorCode.LOGIN_INVALID.getCode()).
                message(e.getMessage()).
                build();
    }

    @ExceptionHandler({BadCredentialsException.class, ProviderNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(Exception e){
        return ErrorResponse.builder().
                errorCode(ErrorCode.BAD_CREDENCIAL.getCode()).
                message(e.getMessage()).
                build();
    }
    @ExceptionHandler({ConvertException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleConvertException(Exception e){
        return ErrorResponse.builder().
                errorCode(ErrorCode.CONVERT_ERROR.getCode()).
                message(e.getMessage()).
                build();
    }
}

