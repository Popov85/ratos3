package ua.edu.ratos.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.session.GenericSessionService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Too long inactivity case.
 * Default authentication session timeout 200 min.
 * (User takes this time to answer up to 200 questions in a single batch,
 *  possibly with no interaction with server).
 * Deal with session data being about to be lost.
 * We presume that business time-out was already fired for this session.
 * It doesn't work for jdbc backed http sessions.
 * @see org.springframework.session.jdbc.JdbcOperationsSessionRepository
 */
@Slf4j
@Component
public class HttpSessionEndedListener implements HttpSessionListener {

    private AppProperties appProperties;

    private GenericSessionService genericSessionService;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Autowired
    public void setGenericSessionService(GenericSessionService genericSessionService) {
        this.genericSessionService = genericSessionService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        log.trace("Http session created, sessionId = {}", event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Object sessionData = session.getAttribute("sessionData");
        if (sessionData!=null) {
            if (appProperties.getSession().isSaveAbandonedResults()) {
                log.debug("Long inactivity case, try to save abandoned results to DB");
                // we presume that time dedicated to this session already gone,
                // then time dedicated to the whole security session was gone as well.
                try {
                    genericSessionService.finish((SessionData) sessionData);
                } catch (Exception e) {
                    log.error("Failed to save an abandoned session, sessionId = {}, message = {}", session.getId(), e.getMessage());
                }
            } else {
                log.debug("Long inactivity case, sessionData will be forever lost...");
            }
        } else {
            log.trace("No sessionData kept in this session, sessionId = {}, just ignore...", session.getId());
        }
    }
}
