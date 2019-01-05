package ua.edu.ratos.service.session;

import ua.edu.ratos.service.domain.StartData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.domain.SessionData;


/**
 * Generic session interface. Defines generic learning session operations in batches;
 * Single question in batch is a particular case (single question batch), no separate processing for it;
 * @author Andrey P.
 */
public interface GenericSessionService {

    /**
     * Starts a new learning session: creates SessionData object to put it into http session
     * @param startData
     * @return the SessionData with BatchOutDto as a part
     */
    SessionData start(StartData startData);

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
     * @return result of this session
     */
    ResultOutDto finish(SessionData sessionData);

    /**
     * Cancel the current dto
     * @param sessionData
     * @return current result
     */
    ResultOutDto cancel(SessionData sessionData);

}
