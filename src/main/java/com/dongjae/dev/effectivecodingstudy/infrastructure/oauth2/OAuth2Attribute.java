package com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2;

import com.dongjae.dev.effectivecodingstudy.common.enums.Const;
import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2.google.GoogleOAuth2UserInfo;
import com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2.kakao.KakaoOAuth2UserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.ProviderNotFoundException;
import java.util.Map;
import java.util.UUID;

import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.ProviderType;
import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.RoleType;


//OAuth2 성공 정보를 담는 클래스
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private static final String GUEST_EMAIL_SUFFIX = "@guest.com";
    private OAuth2UserInfo oAuth2UserInfo;

    public static OAuth2Attribute of(ProviderType provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> ofGoogle(attributes);
            case KAKAO -> ofKakao(attributes);
            default -> throw new ProviderNotFoundException();
        };
    }

    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(ProviderType providerType, OAuth2UserInfo oauthUserInfo) {
        return User.builder()
                .userId(UserId.generate())
                .socialId(oauthUserInfo.getSocialId())
                .Role(RoleType.GUEST)
                .email(UUID.randomUUID() + GUEST_EMAIL_SUFFIX)
                .name(oauthUserInfo.getName())
                .provider(providerType)
                .build();
    }
}
