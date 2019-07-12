package ua.edu.ratos.service.session;

import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.domain.SessionData;

/**
 * Generic session interface. Defines generic learning session operations all in batches;
 * Single question in batch is a particular case (single question batch), there is no separate processing for it;
 * See the only implementation
 * @see ua.edu.ratos.service.session.GenericSessionServiceImpl
 * @author Andrey P.
 */
public interface GenericSessionService {

    /**
     * Starts a new learning session: creates SessionData object to put it into http session
     * @param schemeId id of the requested scheme
     * @param uuid http session unique identifier
     * @return the SessionData with BatchOutDto as a part
     */
    SessionData start(Long schemeId, String uuid);

    /**
     * Provides a new batch of questions for user
     * @param batchInDto currentBatch with user's provided answers
     * @param sessionData
     * @return next batchOutDto
     */
    BatchOutDto next(BatchInDto batchInDto, SessionData sessionData);

    /**
     * Provides the current batch of questions for user;
     * Usually in case of return to already opened session after browser failure
     * @param sessionData
     * @return current batchOutDto
     */
    BatchOutDto current(SessionData sessionData);

    /**
     * Normal finish for basic (non-dynamic sessions)
     * @param sessionData
     * @return result of this session
     */
    ResultOutDto finish(BatchInDto batchInDto, SessionData sessionData);

    /**
     * Normal finish for dynamic sessions
     * @param sessionData
     * @return result of this session
     */
    ResultOutDto finish(SessionData sessionData);

    /**
     * Cancel the current session
     * @param sessionData
     * @return current result
     */
    ResultOutDto cancel(SessionData sessionData);

}
