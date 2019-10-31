package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;

import java.util.List;

@Service
@AllArgsConstructor
public class ResponseProcessor {

    private final EvaluatingService evaluatingService;

    private final ProgressDataService progressDataService;

    private final MetaDataService metaDataService;

    /**
     * Does basic processing of incoming BatchInDto:
     * 1) evaluates it; 2) updates progress and metadata
     * Use it for next and finish requests with last batch needed to be evaluated first.
     * @param batchInDto
     * @param sessionData
     * @return evaluated batch
     */
    public BatchEvaluated doProcessResponse(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        final BatchEvaluated batchEvaluated = evaluatingService.getBatchEvaluated(batchInDto, sessionData);
        // update ProgressData
        progressDataService.update(sessionData, batchEvaluated);
        // update incorrect-s in MetaData (if any)
        List<Long> incorrectResponseIds = batchEvaluated.getIncorrectResponseIds();
        if (!incorrectResponseIds.isEmpty()) metaDataService.createOrUpdateIncorrect(sessionData, incorrectResponseIds);
        return batchEvaluated;
    }
}
