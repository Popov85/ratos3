package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EvaluatingService {

    private static final String EMPTY_BATCH_OUT_EXCEPTION = "Wrong API usage: Failed to get BatchEvaluated, " +
            "cause of empty current batch in SessionData, all questions in the current batch were probably skipped";

    private final BatchEvaluatorService batchEvaluatorService;

    private final BatchEvaluatedBuilder batchEvaluatedBuilderService;

    @Autowired
    public EvaluatingService(@NonNull final BatchEvaluatorService batchEvaluatorService,
                             @NonNull final BatchEvaluatedBuilder batchEvaluatedBuilderService) {
        this.batchEvaluatorService = batchEvaluatorService;
        this.batchEvaluatedBuilderService = batchEvaluatedBuilderService;
    }

    /**
     * Evaluates BatchInDto, make sure current batch to contain some question(s),
     * if not they all probably were skipped and deleted from the current batch (in which case there is nothing to evaluate)
     * @param batchInDto batch in object with provided responses
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return BatchEvaluated object
     */
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        if (!sessionData.getCurrentBatch().isPresent() || sessionData.getCurrentBatch().get().getBatch().isEmpty())
            throw new IllegalArgumentException(EMPTY_BATCH_OUT_EXCEPTION);
        // job to evaluate
        final Map<Long, ResponseEvaluated> responseEvaluated = batchEvaluatorService.doEvaluate(batchInDto, sessionData);
        // Find (if any) add IDs of questions of this batch with incorrect responses
        List<Long> incorrectResponseIds = getIncorrectResponseIds(responseEvaluated);
        return batchEvaluatedBuilderService.build(responseEvaluated, incorrectResponseIds, sessionData);
    }

    /**
     * Finds a list of incorrectly answered questions in the map of evaluated responses
     * @param responseEvaluated map of ResponseEvaluated
     * @return list of incorrectly answered question IDs
     */
    private List<Long> getIncorrectResponseIds(@NonNull final Map<Long, ResponseEvaluated> responseEvaluated) {
        return responseEvaluated
                .values()
                .stream()
                .filter(q -> q.getScore() < 100)
                .map(ResponseEvaluated::getQuestionId)
                .collect(Collectors.toList());
    }

}
