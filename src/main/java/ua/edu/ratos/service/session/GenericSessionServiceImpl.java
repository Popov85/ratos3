package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

/**
 * Universal implementation of the interface for typical learning session.
 */
@Slf4j
@Service
public class GenericSessionServiceImpl implements GenericSessionService {

    @Autowired
    private Timeout timeout;

    private StartProcessingFactory startProcessingFactory;

    private NextProcessingFactory nextProcessingFactory;

    private FinishProcessingFactory finishProcessingFactory;

    private ResponseProcessor responseProcessor;

    private SecurityUtils securityUtils;

    @Autowired
    public void setStartProcessingFactory(StartProcessingFactory startProcessingFactory) {
        this.startProcessingFactory = startProcessingFactory;
    }

    @Autowired
    public void setNextProcessingFactory(NextProcessingFactory nextProcessingFactory) {
        this.nextProcessingFactory = nextProcessingFactory;
    }

    @Autowired
    public void setFinishProcessingFactory(FinishProcessingFactory finishProcessingFactory) {
        this.finishProcessingFactory = finishProcessingFactory;
    }

    @Autowired
    public void setResponseProcessor(ResponseProcessor responseProcessor) {
        this.responseProcessor = responseProcessor;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public SessionData start(@NonNull final Long schemeId, @NonNull final String uuid) {
        StartProcessingService startProcessingService ;
        if (securityUtils.isLmsUser()) {
            startProcessingService = startProcessingFactory.getInstance("lms");
            log.debug("LMS session start processing");
        } else {
            startProcessingService = startProcessingFactory.getInstance("basic");
            log.debug("Non-LMS session start processing");
        }
        return startProcessingService.start(schemeId, uuid);
    }

    @Override
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        NextProcessingService nextProcessingService;
        if (sessionData.isDynamicSession()) {
            nextProcessingService = nextProcessingFactory.getInstance("dynamic");
            log.debug("Dynamic session next processing");
        } else {
            nextProcessingService = nextProcessingFactory.getInstance("basic");
            log.debug("Basic (static) session next processing");
        }
        // Control session time-out
        timeout.controlSessionTimeout();
        return nextProcessingService.next(batchInDto, sessionData);
    }

    @Override
    public BatchOutDto current(@NonNull final SessionData sessionData) {
        timeout.controlSessionTimeout();
        return sessionData.getCurrentBatch().orElseThrow(()->new RuntimeException("Current batch not found!"));
    }

    /**
     * Finish for basic sessions.
     * Use this method for basic sessions when you know which batch is the last and know when to launch finish request.
     * Front-end calls this method as soon as one of these conditions are fulfilled:
     *      1) batchOutDto contains information about batchesLeft = 0;
     *      2) and at the same time the session type is basic (not dynamic!).
     * @param batchInDto
     * @param sessionData
     * @return result DTO (the session's outcome)
     */
    @Override
    public ResultOutDto finish(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        responseProcessor.doProcessResponse(batchInDto, sessionData);
        FinishProcessingService finishProcessingService = finishProcessingFactory.getInstance("regular");
        log.debug("Regular finish with last batch evaluating");
        return finishProcessingService.finish(sessionData);
    }

    /**
     * Finish for dynamic sessions.
     * Front-end calls this method as soon as one of these 3 conditions are fulfilled:
     *   1) method next() returns a batch with empty questions list, which means that there are no more questions left
     *      in the current dynamic session;
     *   2) method next() throws time-out exception;
     *   3) @Deprecated: front-end's timer decides that time is over and launches save request.
     * @param sessionData
     * @return result DTO (the session's outcome)
     */
    @Override
    public ResultOutDto finish(@NonNull final SessionData sessionData) {
        FinishProcessingService finishProcessingService = finishProcessingFactory.getInstance("regular");
        log.debug("Regular finish without last batch evaluating");
        return finishProcessingService.finish(sessionData);
    }

    /**
     * DB operations for cancelled sessions are controlled by settings.
     * @param sessionData session data
     * @return ResultOutDto is shortened to minimum
     */
    @Override
    public ResultOutDto cancel(@NonNull final SessionData sessionData) {
        FinishProcessingService finishProcessingService = finishProcessingFactory.getInstance("cancelled");
        log.debug("Cancelled finish");
        return finishProcessingService.finish(sessionData);
    }

}
