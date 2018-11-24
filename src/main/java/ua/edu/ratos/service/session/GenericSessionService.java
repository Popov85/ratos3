package ua.edu.ratos.service.session;

import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
import ua.edu.ratos.service.session.dto.ResultOutDto;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.Optional;

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
     * @return the SessionData with BatchOutDto as a part
     */
    SessionData start(String key, Long userId, Long schemeId);

    /**
     * Provides a new batch of questions for user
     * @param batchInDto currentBatch with user's provided answers
     * @param sessionData
     * @return next batchOutDto
     */
    BatchOutDto next(BatchInDto batchInDto, SessionData sessionData);

    /**
     * Normal finish, provides results for current learning session
     * @param sessionData
     * @param timeOuted
     * @return result of this session
     */
    ResultOutDto finish(SessionData sessionData, boolean timeOuted);

    /**
     * Cancel the current dto
     * @param sessionData
     * @return current result
     */
    ResultOutDto cancel(SessionData sessionData);

}
