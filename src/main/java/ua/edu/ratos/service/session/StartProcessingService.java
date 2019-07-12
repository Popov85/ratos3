package ua.edu.ratos.service.session;

import ua.edu.ratos.service.NamedService;
import ua.edu.ratos.service.domain.SessionData;

/**
 * See existing implementations:
 * @see ua.edu.ratos.service.session.BasicStartProcessingServiceImpl
 * @see ua.edu.ratos.service.session.LmsStartProcessingServiceImpl
 */
public interface StartProcessingService extends NamedService<String> {

    String NOT_AVAILABLE = "Requested scheme is not available now";

    /**
     * Start a new learning session based on start params
     * @param schemeId
     * @param uuid
     * @return newly created session data
     */
    SessionData start(Long schemeId, String uuid);
}
