package com.dongjae.dev.effectivecodingstudy.application.user;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.UserAlreadyExistsException;
import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.dto.response.SignupResponse;
import com.dongjae.dev.effectivecodingstudy.infrastructure.UserDetailsDao;
import com.dongjae.dev.effectivecodingstudy.repository.UserQueryRepository;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import com.dongjae.dev.effectivecodingstudy.utils.TokenGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupService {
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SignupService(TokenGenerator tokenGenerator,
                         PasswordEncoder passwordEncoder,
                         UserRepository userRepository) {
        this.tokenGenerator = tokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public SignupResponse signup(String username, String password) {
        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("User already exists");
                });

        String encodedPassword = passwordEncoder.encode(password);
        UserId userId = UserId.generate();
        User user = User.builder()
                .userId(userId)
                .username(username)
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        String accessToken = tokenGenerator.generateAccessToken(userId.toString());

        return SignupResponse.builder().
                username(username).
                accessToken(accessToken).
                build();
    }
}
