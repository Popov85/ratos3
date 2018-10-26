package ua.edu.ratos.service.dto.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = "batchMap")
public class BatchOut {
    /**
     * Current batch questions
     */
    private final List<QuestionOutDto> batch;

    /**
     * For look-up purposes (exceptionally)
     */
    @JsonIgnore
    private final Map<Long, QuestionOutDto> batchMap;
    /**
     * Seconds left for this session, -1 if not limited
     */
    private final long timeLeft;
    /**
     * How many question lie ahead in the "queue"
     */
    private final int questionsLeft;

    /**
     * Seconds dedicated for this batch, -1 if not limited
     * After this period of time, client must initiate a request for the next batch, if not 0 batches left
     * Or initiates the finish request if 0 batches left;
     */
    private final long batchTimeControl;
    /**
     * The same value as questionsLeft if batch size is 1 (the most common scenario)
     */
    private final int batchesLeft;

    /**
     * Optional: for extended result on previous batch
     */
    @Setter
    private PreviousBatchResult previousBatchResult;

    public BatchOut(List<QuestionOutDto> batch, long timeLeft, int questionsLeft, long batchTimeControl, int batchesLeft) {
        this.batch = batch;
        this.timeLeft = timeLeft;
        if (timeLeft>0 && batchTimeControl>0 && timeLeft<batchTimeControl)
            throw new IllegalStateException("Time out: no time left for the next batch");
        this.questionsLeft = questionsLeft;
        this.batchTimeControl = batchTimeControl;
        this.batchesLeft = batchesLeft;
        this.batchMap = this.batch
                .stream()
                .collect(Collectors
                        .toMap(q -> q.getQuestionId(), q -> q));
    }
}
