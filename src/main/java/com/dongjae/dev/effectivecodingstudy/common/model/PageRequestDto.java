package com.dongjae.dev.effectivecodingstudy.common.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestDto {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 12;

    private Integer page;
    private Integer size;

    public PageRequestDto() {
        this.page = DEFAULT_PAGE_NUMBER;
        this.size = DEFAULT_PAGE_SIZE;
    }

    public void setPage(int page) {
        this.page = page <= 0 ? DEFAULT_PAGE_NUMBER : page - 1; // 페이지 번호는 0부터 시작
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : DEFAULT_PAGE_SIZE;
    }

    public Pageable toPageable() {
        return PageRequest.of(this.page, this.size);
    }

    // Getter 메서드 생략
}

