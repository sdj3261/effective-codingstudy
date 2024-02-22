package com.dongjae.dev.effectivecodingstudy.security;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.InvalidTokenException;
import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.dto.response.RefreshTokenResponse;
import com.dongjae.dev.effectivecodingstudy.repository.TokenRepository;
import com.dongjae.dev.effectivecodingstudy.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;

    public void updateOrInsertRefreshToken(String username, String refreshToken) {
        Token token = tokenRepository.findById(username)
                .map(existingToken -> {
                    existingToken.setToken(refreshToken);
                    return existingToken;
                })
                .orElseGet(() -> new Token(username, refreshToken));
        tokenRepository.save(token);
    }

    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        if (!tokenGenerator.verifyToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }
        String userId = tokenGenerator.getUserIdFromToken(refreshToken);
        return RefreshTokenResponse.builder().
                accessToken(tokenGenerator.generateAccessToken(userId)).
                build();
    }
}

