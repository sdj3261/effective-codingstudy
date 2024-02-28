package com.dongjae.dev.effectivecodingstudy.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;


public record ProblemListResponse(List<ProblemResponse> problems, int currentPage, int totalItems, int totalPages) {
    @Builder
    public ProblemListResponse(Page<?> page, List<ProblemResponse> problemResponses) {
        this(
                problemResponses,
                page.getNumber(),
                (int) page.getTotalElements(),
                page.getTotalPages()
        );
    }
}

