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
 * Static behaviour: decrement of currentIndex inside sessionData is not possible.
 * You always know exactly how many questions and batches are left because both
 * skip and pyramid features are not possible.
 * You still calculate params of questions and batches left each time,
 * (consider to decrement params instead(!)). All needed params are kept in currentBatch!
 * With this implementation, you always know when to launch finish request (when questionsLeft param = 0).
 * This finish request must also process (evaluate) the last batch in.
 * This is the typical use case for exam types of sessions.
 */
@Slf4j
@Service
public class BasicNextProcessingService implements NextProcessingService {

    private ResponseProcessor responseProcessor;

    private BasicNextBatchBuilder batchBuilder;

    private SessionDataService sessionDataService;

    @Autowired
    public void setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    @Autowired
    public void setBatchBuilder(BasicNextBatchBuilder batchBuilder) {
        this.batchBuilder = batchBuilder;
    }

    @Autowired
    public void setSessionDataService(SessionDataService sessionDataService) {
        this.sessionDataService = sessionDataService;
    }

    @Override
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {

        final BatchEvaluated batchEvaluated = responseProcessor.doProcessResponse(batchInDto, sessionData);

        // Build next BatchOutDto, static behaviour
        BatchOutDto batchOutDto = batchBuilder.build(sessionData, batchEvaluated);

        // update SessionData
        sessionDataService.update(sessionData, batchOutDto);

        return batchOutDto;
    }

    @Override
    public String type() {
        return "basic";
    }
}
