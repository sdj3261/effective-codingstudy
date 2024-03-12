package com.dongjae.dev.effectivecodingstudy.infrastructure.oauth2;

import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import com.dongjae.dev.effectivecodingstudy.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.ProviderType;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("loadUser 호출");
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        // Provider (Google,Kakao,etc)
        ProviderType provider = ProviderType.fromValue(userRequest.getClientRegistration().getRegistrationId());
        // Oauth2Attribute 생성
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(provider, oAuth2User.getAttributes());

        // 기존 회원이면 update, 신규 회원이면 save
        User user = saveOrUpdate(provider, oAuth2Attribute);
        return UserPrincipal.create(user, oAuth2Attribute.getOAuth2UserInfo().attributes);
    }

    public User saveOrUpdate(ProviderType providerType, OAuth2Attribute oAuth2Attribute){
        User user = userRepository.findBySocialId(getSocialIdFromOauth2Attribute(oAuth2Attribute))
                .orElse(oAuth2Attribute.toEntity(providerType,oAuth2Attribute.getOAuth2UserInfo()));

        return userRepository.save(user);
    }

    private String getSocialIdFromOauth2Attribute(OAuth2Attribute oAuth2Attribute) {
        return Optional.ofNullable(oAuth2Attribute)
                .map(OAuth2Attribute::getOAuth2UserInfo)
                .map(OAuth2UserInfo::getSocialId)
                .orElseThrow(()->new OAuth2AuthenticationException("socialID Extract Error"));
    }
}
