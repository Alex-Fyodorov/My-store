package ru.gb.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.event.Level;
import ru.gb.aspect.annotations.RecoverException;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class AnnotationsHandler {

    private final LoggingProperties loggingProperties;

//    @Pointcut("@within(ru.gb.aspect.annotations.Timer)")
//    public void classAnnotatedTimer() {}
//
//    @Pointcut("@annotation(ru.gb.aspect.annotations.Timer)")
//    public void methodAnnotatedTimer() {}

    //@Around("classAnnotatedTimer() || methodAnnotatedTimer()")
    @Around("execution(public * ru.gb.my.market.*.controllers.*.*(..)) || " +
            "@annotation(ru.gb.aspect.annotations.Timer) || @within(ru.gb.aspect.annotations.Timer)")
    public Object timerHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Level level = loggingProperties.getLogLevel();
        Object result = null;
        long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("exception: {}, {}", e.getClass(), e.getMessage());
            throw e;
        }
        long deltaTime = System.currentTimeMillis() - start;
        log.atLevel(level).log("=====   Timer   =====");
        log.atLevel(level).log("{} - {} #({}ms)", joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), deltaTime);
        return result;
    }

//    @Pointcut("@annotation(ru.gb.aspect.annotations.RecoverException)")
//    public void methodAnnotatedRecoverException() {}

    //@Around("methodAnnotatedRecoverException()")
    @Around("@annotation(ru.gb.aspect.annotations.RecoverException)")
    public Object recoverExceptionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Class<? extends RuntimeException>[] exceptions = extractExceptions(joinPoint);
        String returnTypeName = ((MethodSignature)joinPoint.getSignature())
                .getReturnType().getSimpleName();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            if (isExceptionContains(exceptions, e)) {
                throw e;
            } else {
                if (Character.isUpperCase(returnTypeName.charAt(0))) {
                    return null;
                }
                switch (returnTypeName) {
                    case "boolean": return false;
                    case "char": return ' ';
                    case "void": return null;
                    default: return 0;
                }
            }
        }
        return result;
    }

    private Class<? extends RuntimeException>[] extractExceptions(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RecoverException annotation = methodSignature.getMethod()
                .getAnnotation(RecoverException.class);
        if (annotation != null) {
            return annotation.noRecoverFor();
        }
        return null;
    }

    private boolean isExceptionContains(Class<? extends RuntimeException>[] exceptions, Throwable e) {
        if (exceptions != null) {
            for (Class<? extends RuntimeException> exception : exceptions) {
                if (exception.equals(e.getClass())) return true;
                if (exception.isAssignableFrom(e.getClass())) return true;
            }
        }
        return false;
    }
}
