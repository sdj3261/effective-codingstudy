package com.dongjae.dev.effectivecodingstudy.common.dto;

import lombok.*;

@Getter
@Setter
public class BaseResponse<T> {
    private int status;
    private boolean success;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(int status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, true, data);
    }

    public static <T> BaseResponse<T> failure(int status, String message) {
        return new BaseResponse<>(status, false, null);
    }
}

