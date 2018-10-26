package ua.edu.ratos.service.session.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.dto.session.BatchOut;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Scenario 0: Normal finish
 * 1) ResultMock is stored in database
 * 2) User gets result
 * 3) Key is removed from memory programmatically
 *
 * Scenario 1: User is inactive for longer than is set by session settings
 * 1) Data still alive in memory for 12 hours.
 * 2) Next time users requests to continue within TTL time (12 hours), the result is returned.
 * 3) SessionData result is stored in database, with flag expired
 * 4) Key is removed from memory programmatically
 *
 * Scenario 2: User is inactive for more than 12 hours.
 * 1) SessionData data for this key is forever lost in memory due to timeout.
 * 2) User gets his current result if present in incoming BatchOut object.
 * 3) ResultMock is not stored in database.
 */
@Getter
@Setter
@ToString
@RedisHash(value = "session")
public class SessionData implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BUILD_ERROR = "Wrong object state";

    @Id
    private final String key;
    private final Long userId;
    private final Scheme scheme;
    /**
     * Individual list of questions
     */
    private final List<Question> questions;

    /**
     * Exceptionally for look-up by questionId purpose
     */
    @JsonIgnore
    private final Map<Long, Question> questionsMap;

    /**
     * Business time limitation for the whole session, when we consider the session to get timed-out due to business restrictions
     * if LocalDateTime.MAX - not limited in time
     */
    private final LocalDateTime sessionTimeout;

    /**
     * Business time limitation per single question;
     * if <=0, not limited in time
     */
    private final long perQuestionTimeLimit;

    /**
     * In normal case, it is equal to the questionsPerSheet;
     * In fallback cases, it is equal to the size of array
     */
    private final int questionsPerBatch;


    private SessionData(String key,
                Long userId,
                Scheme scheme,
                List<Question> questions,
                int questionsPerBatch,
                LocalDateTime sessionTimeout,
                long perQuestionTimeLimit) {
        this.key = key;
        this.userId = userId;
        this.scheme = scheme;
        this.questions = questions;
        this.questionsPerBatch = questionsPerBatch;
        this.sessionTimeout = sessionTimeout;
        this.perQuestionTimeLimit = perQuestionTimeLimit;
        this.questionsMap = this.questions.stream().collect(Collectors.toMap(q -> q.getQuestionId(), q -> q));
    }

    public static class Builder {
        private String key;
        private Long userId;
        private Scheme scheme;
        private List<Question> questions;
        private int questionsPerBatch;
        private LocalDateTime sessionTimeout;
        private long perQuestionTimeLimit;

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder forUser(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder takingScheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder withIndividualSequence(List<Question> sequence) {
            this.questions = sequence;
            return this;
        }


        public Builder withQuestionsPerBatch(int questionsPerBatch) {
            this.questionsPerBatch = questionsPerBatch;
            return this;
        }

        public Builder withSessionTimeout(LocalDateTime sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public Builder withPerQuestionTimeLimit(long perQuestionTimeLimit) {
            this.perQuestionTimeLimit = perQuestionTimeLimit;
            return this;
        }

        public SessionData build() {
            if (key == null || key.isEmpty()) throw new IllegalStateException(BUILD_ERROR);
            if (userId == null || userId <= 0) throw new IllegalStateException(BUILD_ERROR);
            if (scheme == null) throw new IllegalStateException(BUILD_ERROR);
            if (questions == null || questions.isEmpty()) throw new IllegalStateException(BUILD_ERROR);
            if (questionsPerBatch<=0 || questionsPerBatch>questions.size()) throw new IllegalStateException(BUILD_ERROR);
            if (sessionTimeout==null) throw new IllegalStateException(BUILD_ERROR);
            return new SessionData(key,
                    userId,
                    scheme,
                    questions,
                    questionsPerBatch,
                    sessionTimeout,
                    perQuestionTimeLimit);
        }
    }

    /**
     * Metadata about Question born in the interaction between a user;
     * only for educational sessions, not exams
     * Key is the question's ID
     */
    private Map<Long, MetaData> metaData = new HashMap<>();

    /**
     * Current question index in array, index starting from which we provide questions for the next batch;
     * [currentIndex; currentIndex+batchSize)
     * E.g., currentIndex + batchSize, if currentIndex = 0 and batchSize = 5, currentIndex = 0+5 = 5
     */
    private int currentIndex = 0;

    /**
     * Current BatchOut, convenience state for BatchIn completeness comparison:
     * Whether all the provided questions from BatchOut were found in BatchIn?
     */
    private BatchOut currentBatch = null;

    /**
     * Current BatchOut timeout, if expired - all the questions in the batch (without answer!!!) are considered wrong answered;
     * Client script initiates a new BatchOut request when this time is expired;
     * Default value is MAX (unlimited in time)
     */
    private LocalDateTime currentBatchTimeOut = LocalDateTime.MAX;

    /**
     * When the current batch was created and sent to user
     * (for tracking time spend on a batch)
     */
    private LocalDateTime currentBatchIssued;

    /**
     * Data holder for tracking the current progress;
     * Init with default values
     */
    private ProgressData progressData = new ProgressData();

}
