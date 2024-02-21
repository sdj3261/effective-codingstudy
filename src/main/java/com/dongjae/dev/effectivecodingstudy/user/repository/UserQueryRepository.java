package com.dongjae.dev.effectivecodingstudy.user.repository;


import com.dongjae.dev.effectivecodingstudy.user.domain.User;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findByUsername(String username);
}
