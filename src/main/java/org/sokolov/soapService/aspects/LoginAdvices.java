package org.sokolov.soapService.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoginAdvices {

    private final Logger log = LoggerFactory.getLogger(LoginAdvices.class);

    @Around("execution(* *(..)) && @annotation(Loggable)")
    public Object serviceAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Before execution " + pjp.getSignature().getDeclaringType().getSimpleName() + " "
                + pjp.getSignature().getName() + " passed params " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();
        log.info("After execution " + pjp.getSignature().getDeclaringType().getSimpleName() + " "
                + pjp.getSignature().getName() + " return value " + result.toString());
        return result;
    }
}


