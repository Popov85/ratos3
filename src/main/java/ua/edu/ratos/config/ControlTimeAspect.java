package ua.edu.ratos.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.Timeout;

@Slf4j
@Aspect
@Component
public class ControlTimeAspect {

    @Autowired
    private Timeout timeout;

    @Before("@annotation(ua.edu.ratos.config.ControlTime)")
    public void before(JoinPoint joinPoint) {
        try {
            timeout.setTimeout((SessionData)joinPoint.getArgs()[0]);
        } catch (Exception e) {
            throw new RuntimeException("Make sure SessionData goes as the first argument in your annotated methods!", e);
        }
        log.debug("Has set timeout for request = {}", timeout);
    }
}
