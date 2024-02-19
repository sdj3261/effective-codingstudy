package com.dongjae.dev.effectivecodingstudy.common.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
public class BasicResponse<T> {
    private final boolean success;
    private final T response;
    private final ErrorEntity error;

    @Builder
    public BasicResponse(boolean success, T response, ErrorEntity error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }
}