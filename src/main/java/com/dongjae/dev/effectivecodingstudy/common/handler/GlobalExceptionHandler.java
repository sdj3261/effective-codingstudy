package com.dongjae.dev.effectivecodingstudy.common.handler;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.ConvertException;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.EmailNotFoundException;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.ProviderNotFoundException;
import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorResponse;
import com.dongjae.dev.effectivecodingstudy.utils.ErrorResponseUtil;
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
    public BaseResponse<ErrorResponse> handleUsernameNotFoundException(Exception e) {
        return ErrorResponseUtil.errorResponse(e, ErrorCode.LOGIN_INVALID);
    }

    @ExceptionHandler({BadCredentialsException.class, ProviderNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<ErrorResponse> handleBadCredentialsException(Exception e){
        return ErrorResponseUtil.errorResponse(e, ErrorCode.BAD_CREDENCIAL);
    }
    @ExceptionHandler({ConvertException.class})
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<ErrorResponse> handleConvertException(Exception e){
        return ErrorResponseUtil.errorResponse(e, ErrorCode.CONVERT_ERROR);
    }
}

