package ua.edu.ratos.service.session;

import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.BatchOut;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.session.domain.ResultOut;
import ua.edu.ratos.service.session.domain.SessionData;

/**
 * Generic session interface. Defines generic learning session operations in batches
 * @author Andrey P.
 */
public interface GenericSessionService {

    /**
     * Starts a new learning session
     * @param key
     * @param userId
     * @param schemeId
     * @return the SessionData with BatchOut as a part
     */
    SessionData start(String key, Long userId, Long schemeId);

    /**
     * Provides a new currentBatch of questions
     * @param batchIn currentBatch with user's provided answers
     * @param sessionData
     * @return next batchOut
     */
    BatchOut next(BatchIn batchIn, SessionData sessionData);

    /**
     * Normal finish, provides results for session
     * @param batchIn last BatchIn of questions
     * @param sessionData
     * @param timeOuted
     * @return result on this session
     */
    ResultOutDto finish(BatchIn batchIn, SessionData sessionData, boolean timeOuted);

    /**
     * Cancel the current session
     * @param sessionData
     * @return current result
     */
    ResultOutDto cancel(SessionData sessionData);

}
