package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.Problem;
import com.dongjae.dev.effectivecodingstudy.domain.ProblemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends JpaRepository<Problem, ProblemId> {
}
