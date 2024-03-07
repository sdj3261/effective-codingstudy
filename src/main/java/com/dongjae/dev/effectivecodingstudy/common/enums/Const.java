package com.dongjae.dev.effectivecodingstudy.common.enums;

public class Const {
    public enum ProblemType {
        SQL, ALGORITHM
    }

    public enum ProviderType {
        GOOGLE("google"),
        KAKAO("kakao");
        private final String value;

        ProviderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
        // 문자열 값으로부터 ProviderType을 반환하는 메소드
        public static ProviderType fromValue(String value) {
            for (ProviderType type : values()) {
                if (type.getValue().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }
    }
    public enum RoleType {
        USER,GUEST,ADMIN
        }
}
