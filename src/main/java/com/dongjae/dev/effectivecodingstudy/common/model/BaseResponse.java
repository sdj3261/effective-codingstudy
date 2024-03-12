package com.dongjae.dev.effectivecodingstudy.common.model;

import com.dongjae.dev.effectivecodingstudy.common.enums.ErrorCode;
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

    public static BaseResponse<ErrorResponse> failure(ErrorResponse errorResponse) {
        return new BaseResponse<>(false, errorResponse);
    }

    public static <T> BaseResponse<T> of(boolean success, T data) {
        return new BaseResponse<>(success, data);
    }
}

