package com.dongjae.dev.effectivecodingstudy.dto.response;

import lombok.Builder;

@Builder
public record ProblemResponse (String title, String description, Long difficulty, String tag){
}
