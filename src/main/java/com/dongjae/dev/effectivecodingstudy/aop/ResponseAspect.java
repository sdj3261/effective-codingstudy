package com.dongjae.dev.effectivecodingstudy.aop;

import com.dongjae.dev.effectivecodingstudy.common.exception.GlobalException;
import com.dongjae.dev.effectivecodingstudy.common.model.BaseResponse;
import com.dongjae.dev.effectivecodingstudy.common.model.ErrorEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseAspect {
    // 모든 컨트롤러 메소드에 적용
    @Around("execution(* com.dongjae.dev.effectivecodingstudy..controller..*(..))")
    public Object aroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed(); // 컨트롤러 메소드 실행

            // 메소드 실행 결과가 이미 BaseResponse 인 경우 그대로 반환
            if (result instanceof BaseResponse) {
                return result;
            }

            // 그렇지 않은 경우 BaseResponse로 포장하여 반환
            return BaseResponse.builder()
                    .success(true)
                    .response(result)
                    .build();
        } catch (GlobalException e) {
            // 예외 처리 로직
            ErrorEntity error = new ErrorEntity(e.getErrorCode(),e.getMessage(),null);
            return BaseResponse.builder()
                    .success(false)
                    .error(error)
                    .build();
        }
    }
}
