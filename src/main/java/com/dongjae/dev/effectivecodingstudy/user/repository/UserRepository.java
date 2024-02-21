package com.dongjae.dev.effectivecodingstudy.user.repository;

import com.dongjae.dev.effectivecodingstudy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {
    Optional<User> findByUsernameAndProvider(String username, String provider);
}
