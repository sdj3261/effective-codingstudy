package com.dongjae.dev.effectivecodingstudy.dto.request;

import com.dongjae.dev.effectivecodingstudy.common.model.PageRequestDto;
import lombok.Getter;

public record ProblemRequest (int page, int size, Long difficulty, String tag) {
    public PageRequestDto toPageRequestDto() {
        PageRequestDto dto = new PageRequestDto();
        dto.setPage(this.page);
        dto.setSize(this.size);
        return dto;
    }
}
