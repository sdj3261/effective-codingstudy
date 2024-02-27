package com.dongjae.dev.effectivecodingstudy.repository;

import com.dongjae.dev.effectivecodingstudy.domain.Problem;
import com.dongjae.dev.effectivecodingstudy.domain.QProblem;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ProblemQueryRepositoryImpl implements ProblemQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Problem> findByConditions(UserId userId, Pageable pageable) {
        QProblem problem = QProblem.problem;
        List<Problem> results = queryFactory.selectFrom(problem)
                .where(problem.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(problem)
                .where(problem.user.userId.eq(userId))
                .stream().count();

        return new PageImpl<>(results, pageable, total);
    }
}
