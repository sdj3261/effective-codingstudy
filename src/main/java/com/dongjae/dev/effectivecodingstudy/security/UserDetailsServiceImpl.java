package com.dongjae.dev.effectivecodingstudy.security;

import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new UsernameNotFoundException("CANNOT FIND USER INFO"));

        return UserPrincipal.create(user);
    }
}
