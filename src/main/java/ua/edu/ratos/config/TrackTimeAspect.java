package ua.edu.ratos.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TrackTimeAspect {

    private static final long WARN_THRESHOLD = 500;

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
