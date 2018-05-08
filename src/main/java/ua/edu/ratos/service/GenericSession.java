package ua.edu.ratos.service;

import lombok.NonNull;
import ua.edu.ratos.service.dto.Batch;
import ua.edu.ratos.service.dto.Result;

/**
 * Generic session interface. Defines generic learning session operations
 * @author Andrey P.
 */
public interface GenericSession {

    /**
     * Starts a new learning session
     * @param user
     * @param scheme
     * @return a newly generated key for im-memory storage
     */
    String start(String user, String scheme);

    /**
     * Provides a new batch of questions
     * @param batch batch with user's provided answers
     * @return next batch
     */
    Batch next(Batch batch);

    /**
     * Normal finish, provides results for session
     * @param batch last batch of questions
     * @return
     */
    Result finish(Batch batch);

    /**
     * Session cancelled by user, empty data
     * @param key
     */
    void cancel(String key);


    @Deprecated
    Batch proceed(@NonNull String key);


}
