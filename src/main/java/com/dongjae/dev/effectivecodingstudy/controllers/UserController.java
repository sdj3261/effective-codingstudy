package com.dongjae.dev.effectivecodingstudy.controllers;

import com.dongjae.dev.effectivecodingstudy.application.exceptions.UserAlreadyExistsException;
import com.dongjae.dev.effectivecodingstudy.application.user.LoginService;
import com.dongjae.dev.effectivecodingstudy.application.user.SignupService;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.dto.request.LoginRequest;
import com.dongjae.dev.effectivecodingstudy.dto.request.SignupRequest;
import com.dongjae.dev.effectivecodingstudy.dto.response.AuthResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.LoginResponse;
import com.dongjae.dev.effectivecodingstudy.dto.response.SignupResponse;
import com.dongjae.dev.effectivecodingstudy.oauth2.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final SignupService signupService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse signupResponse = signupService.signup(
                request.username().trim(),
                request.password().trim());

        return BaseResponse.success(signupResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String alreadyExists() {
        return "User Already Exists";
    }
}
