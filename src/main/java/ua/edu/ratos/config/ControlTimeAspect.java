package ua.edu.ratos.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.service.session.SessionDataMap;
import ua.edu.ratos.service.session.Timeout;
import ua.edu.ratos.web.exception.SessionNotFoundException;

@Slf4j
@Aspect
@Component
public class ControlTimeAspect {

    private Timeout timeout;

    @Autowired
    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    /**
     * For every end-point that serves learning session after it has been opened,
     * we control business timeout and check for timeout as early as possible
     * (even for unrestricted in time schemes)
     * in request flow (usually in controllers) and only once within single request!
     * This is advantageous in borderline cases when user finishes learning session
     * just before he runs out of time, and in controller, session is still not expired
     * but during execution process it expires and we have two inconsistent states;
     * Timeout class eliminates this possibility by keeping the same result of validation throughout the request.
     * @param joinPoint
     */
    @Before("@annotation(ua.edu.ratos.config.ControlTime)")
    public void before(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            timeout.setTimeout(((SessionDataMap) args[1]).getOrElseThrow((Long)args[0]));
        } catch(SessionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            //Make sure first argument is schemeId (Long) and
            //second argument is SessionDataMap in your annotated methods!
            throw new RuntimeException("Failed to process aspect", e);
        }
        log.debug("Has set timeout for request = {}", timeout);
    }
}
