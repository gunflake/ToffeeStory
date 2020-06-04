package com.toffeestory.backend.account;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class AccountAspect {

    @Around("execution(* com.toffeestory.backend.account.AccountController.*(..))")
    public Object logging(ProceedingJoinPoint jp) throws Throwable {

        // 메서드가 실행되기 전
        log.info("메서드 명: " + jp.getSignature().getName() + " 시작");
        log.info("파라미터: " + Arrays.toString(jp.getArgs()));

        // 메서드 실행
        Object result = jp.proceed();

        // 메서드가 실행된 후
        log.info("메서드 명: " + jp.getSignature().getName() + " 종료");
        return result;
    }

}
