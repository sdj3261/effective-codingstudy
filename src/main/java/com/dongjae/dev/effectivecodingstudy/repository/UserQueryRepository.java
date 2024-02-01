package com.dongjae.dev.effectivecodingstudy.repository;


import com.dongjae.dev.effectivecodingstudy.entity.User;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findByUsername(String username);
}
