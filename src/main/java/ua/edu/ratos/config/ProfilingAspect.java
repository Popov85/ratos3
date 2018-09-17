package ua.edu.ratos.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ProfilingAspect {

    private static final long WARN_THRESHOLD = 500;

    @Pointcut("execution(* ua.edu.ratos.domain.*.*(..))")
    public void domain(){}

    @Pointcut("execution(*  ua.edu.ratos.service.*.*(..))")
    public void service(){}

    // TODO not working
    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}

/*    @Around("service() || domain()")
    public Object aroundServiceAndDomain(ProceedingJoinPoint joinPoint) throws Throwable {
        return trackTime(joinPoint);
    }*/

    @Around("@annotation(ua.edu.ratos.config.TrackTime)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return trackTime(joinPoint);
    }

    private Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        try {
            sw.start();
            return joinPoint.proceed();
        } finally {
            sw.stop();
            if (sw.getLastTaskTimeMillis()>=WARN_THRESHOLD) {
                log.warn("Time Taken by {} is too long {}"
                        ,joinPoint.getSignature(), sw.prettyPrint());
            } else {
                log.info("Time Taken by {} is {}"
                        ,joinPoint.getSignature(), sw.prettyPrint());
            }
        }
    }
}
