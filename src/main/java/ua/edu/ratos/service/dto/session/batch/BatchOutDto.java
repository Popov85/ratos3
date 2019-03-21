package ua.edu.ratos.service.dto.session.batch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.service.domain.ModeDomain;
import ua.edu.ratos.service.domain.PreviousBatchResult;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;
import ua.edu.ratos.service.session.deserializer.BatchOutDtoDeserializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = "batchMap")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BatchOutDtoDeserializer.class)
public class BatchOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BUILD_ERROR = "Failed to create BatchOutDto: wrong object state";
    private static final String TIMEOUT_ERROR = "Failed to create BatchOutDto: no time left for the next batch";

    private final List<QuestionSessionOutDto> batch;

    @JsonIgnore//For look-up purposes (exceptionally),
    // if there are multiple questions in batch, remains in SessionData
    private final Map<Long, QuestionSessionOutDto> batchMap;

    private final ModeDomain modeDomain;

    private final long timeLeft;

    private final int questionsLeft;

    private final long batchTimeLimit;

    private final int batchesLeft;

    /**
     * Optional (nullable): for extended result on previous batch
     */
    @Setter
    private PreviousBatchResult previousBatchResult;

    private BatchOutDto(final List<QuestionSessionOutDto> batch, final Map<Long, QuestionSessionOutDto> batchMap, final ModeDomain modeDomain,
                        long timeLeft, int questionsLeft, long batchTimeLimit, int batchesLeft, PreviousBatchResult previousBatchResult) {
        this.batch = batch;
        this.modeDomain = modeDomain;
        this.timeLeft = timeLeft;
        this.questionsLeft = questionsLeft;
        this.batchTimeLimit = batchTimeLimit;
        this.batchesLeft = batchesLeft;
        this.batchMap = batchMap;
        this.previousBatchResult = previousBatchResult;
    }

    public static class Builder {
        private List<QuestionSessionOutDto> batch;
        private PreviousBatchResult previousBatchResult;
        private Map<Long, QuestionSessionOutDto> batchMap;
        private ModeDomain modeDomain;
        private long timeLeft;
        private int questionsLeft;
        private long batchTimeControl;
        private int batchesLeft;

        /**
         * Current batch of questions
         */
        public Builder withQuestions(List<QuestionSessionOutDto> questions) {
            this.batch = questions;
            this.batchMap = questions.stream().collect(Collectors.toMap(q -> q.getQuestionId(), q -> q));
            return this;
        }

        /**
         * Use it when no more questions left
         * @return empty batch
         */
        public Builder withNoQuestions() {
            this.batch = new ArrayList<>();
            this.batchMap = new HashMap<>();
            return this;
        }

        /*
         * For frontend to be able to realize what GUI-controls to provide to user during learning session
         */
        public Builder inMode(ModeDomain modeDomain) {
            this.modeDomain = modeDomain;
            return this;
        }

        /**
         * Seconds left for this session, -1 if not limited
         */
        public Builder withTimeLeft(long timeLeft) {
            this.timeLeft = timeLeft;
            return this;
        }

        /**
         * How many question lie ahead in the "queue"
         */
        public Builder withQuestionsLeft(int questionsLeft) {
            this.questionsLeft = questionsLeft;
            return this;
        }

        /**
         * Seconds dedicated for this batch, -1 if not limited
         * After this period of time, client must initiate a request for the next batch, if not 0 batches left
         * Or initiates the save request if 0 batches left;
         */
        public Builder withBatchTimeLimit(long timeLimit) {
            this.batchTimeControl = timeLimit;
            return this;
        }

        /**
         * The same value as questionsLeft if batch size is 1 (the most common scenario)
         */
        public Builder withBatchesLeft(int batchesLeft) {
            this.batchesLeft = batchesLeft;
            return this;
        }

        public Builder withPreviousBatchResult(PreviousBatchResult previousBatchResult) {
            this.previousBatchResult = previousBatchResult;
            return this;
        }

        public BatchOutDto build() {
            if (batch== null || batch.isEmpty()) throw new IllegalStateException(BUILD_ERROR+1);
            if (batchMap == null || batchMap.isEmpty()) throw new IllegalStateException(BUILD_ERROR+2);
            if (modeDomain == null) throw new IllegalStateException(BUILD_ERROR+3);
            if (timeLeft>0 && batchTimeControl>0 && timeLeft<batchTimeControl)
                throw new IllegalStateException(TIMEOUT_ERROR);
            if (questionsLeft<0) throw new IllegalStateException(BUILD_ERROR+4);
            if (batchesLeft<0) throw new IllegalStateException(BUILD_ERROR+5);
            return new BatchOutDto(batch, batchMap, modeDomain, timeLeft, questionsLeft, batchTimeControl, batchesLeft, previousBatchResult);
        }

        public BatchOutDto buildEmpty() {
            return new BatchOutDto(batch, batchMap, modeDomain, timeLeft, questionsLeft, batchTimeControl, batchesLeft, previousBatchResult);
        }
    }

    /**
     * For dynamic types of sessions, when no more questions are left,
     * in order to notify the front-end script to launch finish request.
     * @return batch with empty questions list
     */
    public static BatchOutDto buildEmpty() {
        return new BatchOutDto.Builder()
                .withNoQuestions()
                .inMode(null)
                .withTimeLeft(-1)
                .withQuestionsLeft(0)
                .withBatchTimeLimit(-1)
                .withBatchesLeft(0)
                .buildEmpty();
    }

    public boolean isEmpty() {
        return this.batch.isEmpty();
    }
}
