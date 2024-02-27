package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserQueryRepository {
    Optional<User> findByUsernameAndProvider(String username, String provider);
    Optional<User> findByUsername(String username);
    @Query("SELECT u.username FROM User u WHERE u.id = :id")
    Optional<String> findUsernameById(@Param("id") String userId);
}
