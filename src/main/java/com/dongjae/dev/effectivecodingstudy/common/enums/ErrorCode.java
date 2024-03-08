package com.dongjae.dev.effectivecodingstudy.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    //Auth
    LOGOUT_INDUCED("A001"), // 프론트에서 로그아웃 유도로 사용할 코드
    LOGIN_INVALID("A002"),  // 잘못된 로그인 요청
    TOKEN_ERROR("A003"),  // 토큰 만료
    BAD_CREDENCIAL("A004"), // 자격증명 오류
    //COMMON
    CONVERT_ERROR("C001"); // 자격증명 오류

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

}

