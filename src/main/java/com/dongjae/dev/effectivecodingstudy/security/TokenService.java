package com.dongjae.dev.effectivecodingstudy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.InvalidTokenException;
import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.dto.TokenDto;
import com.dongjae.dev.effectivecodingstudy.dto.response.RefreshTokenResponse;
import com.dongjae.dev.effectivecodingstudy.repository.TokenRepository;
import com.dongjae.dev.effectivecodingstudy.utils.TokenGenerator;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

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

    public void upsertRefreshToken(String username, String refreshToken) {
        tokenRepository.findByUsername(username)
                .filter(token -> !tokenGenerator.verifyToken(token.getToken())) // Token이 유효하지 않은 경우
                .map(token -> toEntity(new TokenDto(username,refreshToken)))// 새 Token 객체로 매핑
                .ifPresent(tokenRepository::save); // 새 Token이 있으면 저장
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

    private Token toEntity(TokenDto tokenDto) {
        return new Token(tokenDto.getUsername(), tokenDto.getToken());
    }
}

