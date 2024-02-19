package com.dongjae.dev.effectivecodingstudy.common;

public enum ErrorCode {
    //Auth
    LOGOUT_INDUCED("A001"), // 프론트에서 로그아웃 유도로 사용할 코드
    LOGIN_INVALID("A002");  // 잘못된 로그인 요청

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

