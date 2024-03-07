package com.dongjae.dev.effectivecodingstudy.security;

import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.RoleType;
@AllArgsConstructor
@Builder
@ToString(of= {"userId"})
@Getter
public class UserPrincipal implements UserDetails, OAuth2User {
    private UserId userId;
    private String email;
    private String name;
    private RoleType role;
    private String nickname;
    private Map<String, Object> oauth2UserAttributes;

    public static UserPrincipal create(User user, Map<String, Object> oauth2UserAttributes) {
        return UserPrincipal.builder().
                userId(user.getUserId()).
                email(user.getEmail()).
                name(user.getName()).
                role(user.getRole()).
                nickname(user.getNickname()).
                oauth2UserAttributes(oauth2UserAttributes).
                build();
    }

    public static UserPrincipal create(User user) {
        return UserPrincipal.builder().
                userId(user.getUserId()).
                email(user.getEmail()).
                name(user.getName()).
                role(user.getRole()).
                nickname(user.getNickname()).
                build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 역할에 따라 GrantedAuthority 생성
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.name()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @Nullable
    public <A> A getAttribute(String name) {
        return (A) oauth2UserAttributes.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(oauth2UserAttributes);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
