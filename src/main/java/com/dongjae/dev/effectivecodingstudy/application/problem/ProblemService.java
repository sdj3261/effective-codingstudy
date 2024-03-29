package com.dongjae.dev.effectivecodingstudy.application.problem;

import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.PageRequestDto;
import com.dongjae.dev.effectivecodingstudy.domain.Problem;
import com.dongjae.dev.effectivecodingstudy.domain.UserId;
import com.dongjae.dev.effectivecodingstudy.dto.request.ProblemRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.ProblemListResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.ProblemResponse;
import com.dongjae.dev.effectivecodingstudy.repository.ProblemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemQueryRepository problemQueryRepository;

    @Transactional(readOnly = true)
    public BaseResponse<ProblemListResponse> getProblemsList(UserId userId, ProblemRequest request) {
        Page<Problem> problemPage = problemQueryRepository.findByConditions(userId, request.toPageRequestDto().toPageable());

//        List<ProblemResponse> problemResponses = problemPage.getContent().stream()
//                .map(problem -> EntityDtoConverter.convert(problem, ProblemResponse.class))
//                .collect(Collectors.toList());

        // Use the new constructor
        return BaseResponse.success(ProblemListResponse.builder().
                problemResponses(null).
                page(problemPage).
                build());
    }
}