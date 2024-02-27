package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.Problem;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemQueryRepository {
    Page<Problem> findByConditions(UserId userId, Pageable pageable);
}
