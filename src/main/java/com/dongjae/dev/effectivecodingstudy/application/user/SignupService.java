package com.dongjae.dev.effectivecodingstudy.application.user;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.UserAlreadyExistsException;
import com.dongjae.dev.effectivecodingstudy.infrastructure.UserDetailsDao;
import com.dongjae.dev.effectivecodingstudy.utils.AccessTokenGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupService {
    private final AccessTokenGenerator accessTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsDao userDetailsDao;

    public SignupService(AccessTokenGenerator accessTokenGenerator,
                         PasswordEncoder passwordEncoder,
                         UserDetailsDao userDetailsDao) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsDao = userDetailsDao;
    }

    public String signup(String username, String password) {
        if (userDetailsDao.existsByUsername(username)) {
            throw new UserAlreadyExistsException();
        }

        String id = TSID.Factory.getTsid().toString();
        String encodedPassword = passwordEncoder.encode(password);
        String accessToken = accessTokenGenerator.generateAccessToken(id);

        userDetailsDao.addUser(id, username, encodedPassword);
        userDetailsDao.addAccessToken(id, accessToken);

        return accessToken;
    }
}
