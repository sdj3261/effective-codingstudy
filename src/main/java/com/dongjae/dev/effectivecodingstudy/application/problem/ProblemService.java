package com.dongjae.dev.effectivecodingstudy.application.problem;

import com.dongjae.dev.effectivecodingstudy.domain.Problem;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.repository.ProblemQueryRepository;
import com.dongjae.dev.effectivecodingstudy.repository.ProblemQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemQueryRepository problemQueryRepository;

    public Page<Problem> getProblemsList(UserId userId, Pageable pageable) {
        return problemQueryRepository.findByConditions(userId, pageable);
    }
}