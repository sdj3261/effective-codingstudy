package com.dongjae.dev.effectivecodingstudy.application.user;

import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
    public UserPrincipal getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("CANNOT FIND USER INFO");
        }
        return (UserPrincipal) authentication.getPrincipal();
    }
}
