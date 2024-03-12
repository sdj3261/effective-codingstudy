package com.dongjae.dev.effectivecodingstudy.application.user;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.UserAlreadyExistsException;
import com.dongjae.dev.effectivecodingstudy.common.enums.Const;
import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.dto.response.SignupResponse;
import com.dongjae.dev.effectivecodingstudy.repository.UserRepository;
import com.dongjae.dev.effectivecodingstudy.security.TokenGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.dongjae.dev.effectivecodingstudy.common.enums.Const.RoleType;

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

    public SignupResponse signup(String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("User already exists");
                });

        String encodedPassword = passwordEncoder.encode(password);
        UserId userId = UserId.generate();
        User user = User.builder()
                .userId(userId)
                .Role(RoleType.GUEST)
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        String accessToken = tokenGenerator.generateAccessToken(userId.toString());

        return SignupResponse.builder().
                accessToken(accessToken).
                build();
    }
}
