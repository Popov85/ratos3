package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

/**
 * Dynamic behaviour: increment as well as decrement of currentIndex inside sessionData is possible.
 * You never know exactly how many questions and batches are left because both
 * skip and pyramid are possible, which both add dynamic change
 * to the session data state (in sessionData, currentIndex can change to the opposite direction! e.g. (7->6)),
 * and hence you need to re-calculate again how many questions and batches are left after each batch round-trip.
 * With this implementation, front-end script decides when to launch finish request (with no batchIn) only when it has received an empty batchOut.
 * This is the typical case for educational types of sessions.
 */
@Slf4j
@Service
public class DynamicNextProcessingService implements NextProcessingService {

    private TimingService timingService;

    private ResponseProcessor responseProcessor;

    private PyramidService pyramidService;

    private DynamicNextBatchBuilder batchBuilder;

    private SessionDataService sessionDataService;

    @Autowired
    public void setTimingService(TimingService timingService) {
        this.timingService = timingService;
    }

    @Autowired
    public void setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    @Autowired
    public void setPyramidService(PyramidService pyramidService) {
        this.pyramidService = pyramidService;
    }

    @Autowired
    public void setBatchBuilder(DynamicNextBatchBuilder batchBuilder) {
        this.batchBuilder = batchBuilder;
    }

    @Autowired
    public void setSessionDataService(SessionDataService sessionDataService) {
        this.sessionDataService = sessionDataService;
    }


    @Override
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {

        // Consider the client script to initiate save request after either batch timeout or session timeout
        timingService.control(sessionData.getSessionTimeout(), sessionData.getCurrentBatchTimeOut());

        // Build next BatchOutDto
        BatchOutDto batchOutDto;
        // If current BatchOutDto contains smth. (we check because in case of skipping we remove elements from it)
        if (sessionData.getCurrentBatch().isPresent() &&
                !sessionData.getCurrentBatch().get().isEmpty()) {

            final BatchEvaluated batchEvaluated = responseProcessor.doProcessResponse(batchInDto, sessionData);

            // Pyramid mode processing if enabled
            if (sessionData.getSchemeDomain().getModeDomain().isPyramid()) {
                pyramidService.process(sessionData, batchEvaluated);
            }

            if (!sessionData.hasMoreQuestions()) {
                batchOutDto = BatchOutDto.buildEmpty();
            } else {
                batchOutDto = batchBuilder.build(sessionData, batchEvaluated);
            }
        } else {// All questions were skipped: nothing to evaluate
            if (!sessionData.hasMoreQuestions()) {
                batchOutDto = BatchOutDto.buildEmpty();
            } else {
                // Build BatchOutDto with no batchEvaluated
                batchOutDto = batchBuilder.build(sessionData);
            }
        }
        // update SessionData
        if (!batchOutDto.isEmpty()) sessionDataService.update(sessionData, batchOutDto);
        return batchOutDto;
    }

    @Override
    public String type() {
        return "dynamic";
    }
}
