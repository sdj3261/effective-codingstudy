package com.dongjae.dev.effectivecodingstudy.security;

import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.domain.TokenId;
import com.dongjae.dev.effectivecodingstudy.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void updateOrInsertRefreshToken(String username, String refreshToken) {
        Token token = tokenRepository.findById(username)
                .map(existingToken -> {
                    existingToken.setToken(refreshToken);
                    return existingToken;
                })
                .orElseGet(() -> new Token(username, refreshToken));
        tokenRepository.save(token);
    }
}

