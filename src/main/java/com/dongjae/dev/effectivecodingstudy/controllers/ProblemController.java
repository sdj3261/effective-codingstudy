package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.application.problem.ProblemService;
import com.dongjae.dev.effectivecodingstudy.application.user.LoginService;
import com.dongjae.dev.effectivecodingstudy.application.user.UserSessionService;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.PageRequestDto;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.request.ProblemRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.ProblemListResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.ProblemResponse;
import com.dongjae.dev.effectivecodingstudy.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {
    private final ProblemService problemService;
    private final UserSessionService userSessionService;

    @GetMapping
    public BaseResponse<ProblemListResponse> listProblem(ProblemRequest request) {
        return problemService.getProblemsList(
                userSessionService.getCurrentUserDetails().getUserId(), request);
    }
}
