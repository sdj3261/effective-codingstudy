package com.dongjae.dev.effectivecodingstudy.utils;

import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorResponse;
import org.apache.http.HttpStatus;

public class ErrorResponseUtil {
    public static BaseResponse<ErrorResponse> errorResponse(Exception e, ErrorCode errorCode) {
        return BaseResponse.failure(ErrorResponse.builder()
                .errorCode(errorCode)
                .message(e.getMessage())
                .build());
    }
    public static BaseResponse<ErrorResponse> errorResponse(String msg, ErrorCode errorCode) {
        return BaseResponse.failure(ErrorResponse.builder()
                .errorCode(errorCode)
                .message(msg)
                .build());
    }
}
