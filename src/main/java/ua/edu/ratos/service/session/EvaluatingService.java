package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluatingService {

    private static final String EMPTY_BATCH_OUT_EXCEPTION = "Wrong API usage: Failed to get BatchEvaluated, " +
            "cause of empty current batch in SessionData, all questions in the current batch were probably skipped";

    private final Timeout timeout;

    private final BatchEvaluatorService batchEvaluatorService;

    /**
     * Evaluates BatchInDto, make sure current batch to contain some question(s),
     * if not they all probably were skipped and deleted from the current batch (in which case there is nothing to evaluate)
     * @param batchInDto batch in object with provided responses
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return BatchEvaluated object
     */
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        if (!sessionData.getCurrentBatch().isPresent() || sessionData.getCurrentBatch().get().getQuestions().isEmpty())
            throw new IllegalArgumentException(EMPTY_BATCH_OUT_EXCEPTION);
        // job to evaluate
        final List<ResponseEvaluated> responseEvaluated = batchEvaluatorService.doEvaluate(batchInDto, sessionData);
        // Calculate time spent
        final LocalDateTime currentBatchIssued = sessionData.getCurrentBatchIssued();
        final long timeSpent = TimingService.getTimeSpent(currentBatchIssued);
        // Is this batch or the whole session time-outed?
        return new BatchEvaluated(responseEvaluated, timeSpent, timeout.isTimeouted());
    }

}
