package com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2.kakao;

import com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2.OAuth2UserInfo;

import java.util.Map;
import java.util.Optional;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attributes)
                .map(attrs -> (Map<String, Object>) attrs.get("properties"))
                .map(props -> (String) props.get("nickname"))
                .orElseThrow(() -> new IllegalArgumentException("Nickname is required"));
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (profile == null) {
            return null;
        }

        return (String) profile.get("thumbnail_image_url");
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        return (String) account.get("email");
    }
}
