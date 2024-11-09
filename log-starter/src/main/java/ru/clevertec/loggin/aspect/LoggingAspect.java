package ru.clevertec.loggin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void annotationPointcut() {
        // Pointcut for #logMethod
    }

    @Pointcut("within(ru.clevertec..*)")
    public void applicationPackagePointcut() {
        // Pointcut for #logMethod
    }

    @Around("annotationPointcut() && applicationPackagePointcut()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!log.isDebugEnabled()) {
            return joinPoint.proceed();
        }
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result);
        return result;
    }
}
