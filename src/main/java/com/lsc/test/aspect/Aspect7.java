package com.lsc.test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

@org.springframework.stereotype.Service
@org.aspectj.lang.annotation.Aspect
public class Aspect7 {

    @Pointcut("execution(* com.lsc.test.service..*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object handleServiceMethod(ProceedingJoinPoint pjp){
        Object data ;
        try {
            data = pjp.proceed();
            return data;
        } catch (Throwable throwable) {
            return null;
        }
    }

}
