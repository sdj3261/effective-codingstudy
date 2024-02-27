package com.dongjae.dev.effectivecodingstudy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dongjae.dev.effectivecodingstudy.application.exceptions.InvalidTokenException;
import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.dto.TokenDto;
import com.dongjae.dev.effectivecodingstudy.dto.response.TokenResponse;
import com.dongjae.dev.effectivecodingstudy.repository.TokenRepository;
import com.dongjae.dev.effectivecodingstudy.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;

    public TokenResponse refreshAccessToken(String refreshToken) {
        if (tokenGenerator.verifyToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }
        String userId = tokenGenerator.getUserIdFromToken(refreshToken);
        String newAccessToken = tokenGenerator.generateAccessToken(userId);
        String newRefreshToken = isRefreshTokenExpiring(refreshToken) ?
                tokenGenerator.generateRefreshToken(userId) : refreshToken;

        return TokenResponse.builder().
                accessToken(newAccessToken).
                refreshToken(newRefreshToken).
                build();
    }

    // 리프레시 토큰의 만료가 임박했는지 확인하는 함수
    private boolean isRefreshTokenExpiring(String refreshToken) {
        //만료 기준을 1일로 설정
        final long threshold = 24;
        try {
            // 토큰 디코드
            DecodedJWT decodedToken = JWT.decode(refreshToken);
            // 토큰의 만료 시간(exp) 추출
            Instant expiration = decodedToken.getExpiresAt().toInstant();
            // 현재 시간과의 차이를 계산
            long hoursUntilExpiration = ChronoUnit.HOURS.between(Instant.now(), expiration);
            return hoursUntilExpiration < threshold;
        } catch (Exception e) {
            return false;
        }
    }

    public String getOrCreateRefreshToken(String username, UserId userId) {
        // findByUsername 메서드가 Optional<Token>을 반환한다고 가정
        return tokenRepository.findByUsername(username)
                .map(Token::getToken) // Token 객체에서 토큰 문자열 추출
                .orElseGet(() -> {
                    // 토큰 문자열이 없는 경우 새로운 토큰 생성 및 저장
                    String newToken = tokenGenerator.generateRefreshToken(userId.toString());
                    Token refreshToken = new Token(username, newToken);
                    tokenRepository.save(refreshToken); // 새로운 토큰 저장
                    return newToken; // 저장된 새 토큰 반환
                });
    }


    private Token toEntity(TokenDto tokenDto) {
        return new Token(tokenDto.getUsername(), tokenDto.getToken());
    }
}

