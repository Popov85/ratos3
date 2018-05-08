package ua.edu.ratos.service.dto;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import ua.edu.ratos.domain.Question;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Scenario 0: Normal finish
 * 1) Result is stored in database
 * 2) User gets result
 * 3) Key is removed from memory programmatically
 *
 * Scenario 1: User is inactive for longer than is set by session settings
 * 1) Data still alive in memory for 12 hours.
 * 2) Next time users requests to continue within TTL time (12 hours), the result is returned.
 * 3) Session result is stored in database, with flag expired
 * 4) Key is removed from memory programmatically
 *
 * Scenario 2: User is inactive for more than 12 hours.
 * 1) Session data for this key is forever lost in memory due to timeout.
 * 2) User gets his current result if present in incoming Batch object.
 * 3) Result is not stored in database.
 */
@Getter
@Setter
@ToString
@RedisHash(value = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String key;
    private final String user;
    private final String scheme;
    /**
     * Individual list of questions
     */
    private final List<Question> questions;

    public Session(@NonNull String key, @NonNull String user, @NonNull String scheme, List<Question> questions) {
        this.key = key;
        this.user = user;
        this.scheme = scheme;
        this.questions = questions;
    }

    @TimeToLive(unit=TimeUnit.HOURS)
    private final long timeout = 12L;

    /**
     * Current question index
     */
    public int index;
    /**
     * Current Batch index
     */
    public int batch;



}
