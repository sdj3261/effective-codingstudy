package com.dongjae.dev.effectivecodingstudy.repository;


import com.dongjae.dev.effectivecodingstudy.domain.User;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findByEmail(String email);
}
