package com.dongjae.dev.effectivecodingstudy.common.model;

import lombok.*;

@Getter
@Setter
public class BaseResponse<T> {
    private boolean success;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, data);
    }

    public static BaseResponse<ErrorResponse> failure(String errorCode, String message, String details) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .details(details)
                .build();
        return new BaseResponse<>(false, errorResponse);
    }
    //유연하게 처리하기 위해 생성
    public static <T> BaseResponse<T> of(boolean success, T data) {
        return new BaseResponse<>(success, data);
    }
}

