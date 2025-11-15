package com.cub.coindesk.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.cub.coindesk..*Controller.*(..))")
    public Object logApi(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("API call: {} args={}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
        try {
            Object result = pjp.proceed();
            log.info("API result: {} duration={}ms", pjp.getSignature(), System.currentTimeMillis()-start);
            return result;
        } catch (Throwable t) {
            log.error("API error: {} message={}", pjp.getSignature(), t.getMessage());
            throw t;
        }
    }
}
