package ua.edu.ratos.service;

import lombok.NonNull;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.BatchOut;
import ua.edu.ratos.domain.model.Result;

/**
 * Generic batched session interface. Defines generic learning session operations in batches
 * @author Andrey P.
 */
public interface GenericSessionService {

    /**
     * Starts a new learning session
     * @param user
     * @param scheme
     * @return a newly generated key for in-memory storage
     */
    String start(String user, String scheme);

    /**
     * Provides a new batch of questions
     * @param batchIn batch with user's provided answers
     * @return next batchOut
     */
    BatchOut next(BatchIn batchIn);

    /**
     * Normal finish, provides results for session
     * @param batchIn last batch of questions
     * @return
     */
    Result finish(BatchIn batchIn);

    /**
     * SessionData cancelled by user, empty data
     * @param key
     * @return current result
     */
    Result cancel(String key);

}
