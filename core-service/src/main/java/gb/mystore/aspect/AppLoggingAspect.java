package gb.mystore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AppLoggingAspect {

    @Around("execution(public * gb.mystore.controllers.*.*(..))")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        log.info("=======================");
        log.info(String.format("%s.%s - %sms",
                proceedingJoinPoint.getClass().getSimpleName(),
                proceedingJoinPoint.getSignature(), duration));
        return out;
    }
}
