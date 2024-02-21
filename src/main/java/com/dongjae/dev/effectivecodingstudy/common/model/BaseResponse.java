package com.dongjae.dev.effectivecodingstudy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
public class BaseResponse<T> {
    private int status; // HTTP 상태 코드
    private boolean success; // 응답 메시지
    private T data; // 실제 응답 데이터

    public BaseResponse() {
    }

    public BaseResponse(int status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    // 성공 응답을 생성하는 편의 메소드
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, true, data);
    }

    // 실패 응답을 생성하는 편의 메소드
    public static <T> BaseResponse<T> failure(int status, String message) {
        return new BaseResponse<>(status, false, null);
    }
}

