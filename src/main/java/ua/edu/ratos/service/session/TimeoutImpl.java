package ua.edu.ratos.service.session;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.web.exception.RunOutOfTimeException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;

/**
 * This class aims to keep timeout info throughout the request.
 * Set timeout info as early as possible in request flow and then use getters to check if batch or the whole session was timeouted.
 * A separate class is needed to eliminate borderline cases when multiple checks are needed in different places (methods)
 * of the current thread stack. Thus, we always get a consistent (across request) value for boolean timeouts.
 */
@Slf4j
@Getter
@Setter
@ToString
@Component
@RequestScope
@Profile({"prod", "dev"})
public class TimeoutImpl implements Timeout {
    /**
     * Add 3 or more seconds (experimental value) to compensate the network round trips time;
     * Probably, the  client script would initiate the request to server in case of time-out either session or batch (not confirmed);
     * In turn, server also must check the timeout, but adds 3 or more sec to compensate the network traverse time
     */
    private static final long NETWORK_LEEWAY_SECONDS = 3;

    private boolean isSessionTimeout;

    private boolean isBatchTimeout;

    // Flag indicating that state has already been set within this request scope
    private boolean isSet;

    @Override
    public void setTimeout(@NonNull final SessionData sessionData) {
        if (isSet) throw
                new IllegalStateException("Timeout state is already set: state sets only once at the start of request flow");
        this.isSessionTimeout = isTimeoutedWithLeeway(sessionData.getSessionTimeout());
        this.isBatchTimeout = isTimeoutedWithLeeway(sessionData.getCurrentBatchTimeOut());
        this.isSet = true;
    }

    @Override
    public void controlSessionTimeout() {
        if (!isSet) throw new IllegalStateException("Timeout state is not set yet!");
        if (isSessionTimeout) throw new RunOutOfTimeException();
    }

    @Override
    public boolean isTimeouted() {
        if (!isSet) throw new IllegalStateException("Timeout state is not set yet!");
        return (isSessionTimeout || isBatchTimeout);
    }

    private boolean isTimeoutedWithLeeway(LocalDateTime businessTimeout) {
        if (TimingService.isUnlimited(businessTimeout)) return false;
        return LocalDateTime.now().isAfter(businessTimeout.plusSeconds(NETWORK_LEEWAY_SECONDS));
    }

    @PostConstruct
    public void init() {
        log.debug("Bean Timeout constructed = {}", this);
    }

    @PreDestroy
    public void destroy() {
        log.debug("Bean Timeout destroyed = {}", this);
    }
}
