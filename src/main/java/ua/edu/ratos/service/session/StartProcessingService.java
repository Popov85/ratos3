package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.StartData;

/**
 *  See existing implementations:
 * @see ua.edu.ratos.service.session.BasicStartProcessingServiceImpl
 * @see ua.edu.ratos.service.session.LmsStartProcessingServiceImpl
 */
public interface StartProcessingService {

    String NOT_AVAILABLE = "Requested scheme is not available now";

    /**
     * Do start a new session based on startData params
     * @param startData
     * @return newly created session data
     */
    SessionData start(StartData startData);

    String type();
}
