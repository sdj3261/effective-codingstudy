package com.dongjae.dev.effectivecodingstudy.security;

import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.repository.TokenRepository;
import com.dongjae.dev.effectivecodingstudy.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
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

    public String validateRefreshToken(Token refreshToken){
        // refresh 객체에서 refreshToken 추출
        String token = refreshToken.getToken();

        try {
            tokenGenerator.verifyToken(token);
        }catch (Exception e) {
            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;

        }

        return null;
    }
    }
}

