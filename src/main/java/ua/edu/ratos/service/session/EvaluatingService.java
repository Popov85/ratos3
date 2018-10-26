package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.ProgressData;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EvaluatingService {

    @Autowired
    private BatchEvaluatorService batchEvaluatorService;

    @Autowired
    private BatchEvaluatedBuilderService batchEvaluatedBuilderService;

    @Autowired
    private SkipProcessorService skipProcessorService;

    @Autowired
    private PyramidProcessorService pyramidProcessorService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private MetaDataService metaDataService;




    /**
     * This implementation is able to process skipping and pyramid scenarios
     * and
     * keeps meta data about users choices/performance on given questions
     * @param batchIn
     * @param sessionData
     * @return BatchEvaluated object or null if nothing was to evaluate, (all skipped)
     */
    public Optional<BatchEvaluated> evaluate(@NonNull final BatchIn batchIn, @NonNull SessionData sessionData) {
        BatchEvaluated batchEvaluated = null;
        // Skip processing
        // 1. Get skipped question list if any, if no - just proceed to evaluate the BatchIn;
        final List<Long> skipped = skipProcessorService.getSkipped(batchIn, sessionData);
        if (!skipped.isEmpty()) {
            // shift skipped questions to the end of questions list
            shiftService.doShift(skipped, sessionData);
        }
        // Evaluate processing
        final List<Long> toEvaluate = skipProcessorService.getNotSkipped(skipped, sessionData);
        if (!toEvaluate.isEmpty()) {
            // job to evaluate
            final Map<Long, ResponseEvaluated> responseEvaluated = batchEvaluatorService
                    .doEvaluate(batchIn, toEvaluate, sessionData);
            batchEvaluated = batchEvaluatedBuilderService
                    .build(responseEvaluated, batchIn.getOptions(), sessionData);
            // update ProgressData
            final ProgressData updatedProgressData = progressDataService.getUpdated(sessionData, batchEvaluated);
            sessionDataService.update(sessionData, updatedProgressData);
            // control pyramid if enabled
            final List<Long> incorrect = pyramidProcessorService.getIncorrect(sessionData, batchEvaluated);
            if (!incorrect.isEmpty()) {
                // shift skipped questions to the end of questions list
                shiftService.doShift(incorrect, sessionData);
            }
            metaDataService.update(sessionData, batchEvaluated);
        } else {// nothing to evaluate, (all is skipped probably)
            // update meta data simply
            metaDataService.update(sessionData, batchIn);
        }
        return Optional.of(batchEvaluated);
    }

}
