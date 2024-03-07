package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.User;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId>, UserQueryRepository {
    Optional<User> findBySocialId(String socialId);
    Optional<User> findByEmailAndProvider(String email, String provider);
    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    Optional<String> findUsernameByUserId(@Param("id") UserId userId);
}
