package com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2;


import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getSocialId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id"


    public abstract String getImageUrl();

    public abstract String getEmail();
    public abstract String getName();
}