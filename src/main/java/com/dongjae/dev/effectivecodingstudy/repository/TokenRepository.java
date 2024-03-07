package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.Token;
import com.dongjae.dev.effectivecodingstudy.domain.TokenId;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, UserId> {
}
