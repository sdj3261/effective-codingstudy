package com.dongjae.dev.effectivecodingstudy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;


@Getter
public class BaseResponse<T> {
    private final boolean success;
    private final T response;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ErrorEntity error;

    @Builder
    public BaseResponse(boolean success, T response, ErrorEntity error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }
}