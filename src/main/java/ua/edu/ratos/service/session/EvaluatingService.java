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

    private static final String EMPTY_BATCH_OUT = "Empty BatchOutDto, all questions in the current batch were probably skipped";

    @Autowired
    private BatchEvaluatorService batchEvaluatorService;

    @Autowired
    private BatchEvaluatedBuilder batchEvaluatedBuilderService;

    /**
     * Evaluates BatchInDto, make sure BatchInDto to contain non-empty collection of responses
     * @param batchInDto
     * @param sessionData
     * @return BatchEvaluated object
     */
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull SessionData sessionData) {
        if (sessionData.getCurrentBatch().getBatch().isEmpty()) throw new IllegalArgumentException(EMPTY_BATCH_OUT);
        // job to evaluate
        final Map<Long, ResponseEvaluated> responseEvaluated = batchEvaluatorService.doEvaluate(batchInDto, sessionData);
        // select ids with incorrect responses
        List<Long> incorrectResponseIds = getIncorrectResponseIds(responseEvaluated);
        return batchEvaluatedBuilderService.build(responseEvaluated, incorrectResponseIds, sessionData);
    }

    /**
     * Gets list of incorrectly answered questions in the map of evaluated responses
     * @param responseEvaluated
     * @return list of incorrectly answered question IDs
     */
    private List<Long> getIncorrectResponseIds(@NonNull final Map<Long, ResponseEvaluated> responseEvaluated) {
        return responseEvaluated
                .values()
                .stream()
                .filter(q -> q.getScore() < 100)
                .map(q -> q.getQuestionId())
                .collect(Collectors.toList());
    }

}
