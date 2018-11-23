package ua.edu.ratos.service.session;

import ua.edu.ratos.service.session.domain.batch.BatchIn;
import ua.edu.ratos.service.session.domain.batch.BatchOut;
import ua.edu.ratos.service.session.dto.ResultOutDto;
import ua.edu.ratos.service.session.domain.SessionData;

/**
 * Generic session interface. Defines generic learning session operations in batches;
 * Single question in batch is a particular case (single question batch), no separate processing for it;
 * @author Andrey P.
 */
public interface GenericSessionService {

    /**
     * Starts a new learning session: creates SessionData object to put it into http session
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
     * Normal finish, provides results for dto
     * @param batchIn last BatchIn of questions
     * @param sessionData
     * @param timeOuted
     * @return result on this session
     */
    ResultOutDto finish(BatchIn batchIn, SessionData sessionData, boolean timeOuted);

    /**
     * Cancel the current dto
     * @param sessionData
     * @return current result
     */
    ResultOutDto cancel(SessionData sessionData);

}
