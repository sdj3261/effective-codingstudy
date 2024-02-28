package com.dongjae.dev.effectivecodingstudy.dto.request;

import com.dongjae.dev.effectivecodingstudy.common.model.PageRequestDto;
import lombok.Getter;

public record ProblemRequest (PageRequestDto pageRequestDto, Long difficulty, String tag){
}
