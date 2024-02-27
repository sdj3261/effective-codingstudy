package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.application.user.LoginService;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.PageRequestDto;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.request.ProblemRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.ProblemResponse;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {
    @GetMapping
    public BaseResponse<ProblemResponse> listProblem(ProblemRequest request) {
        return BaseResponse.success(new ProblemResponse());
    }
}
