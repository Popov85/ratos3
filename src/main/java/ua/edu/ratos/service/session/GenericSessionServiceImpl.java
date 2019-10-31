package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

import java.util.Optional;

/**
 * Universal implementation of the interface for typical learning session.
 */
@Slf4j
@Service
@AllArgsConstructor
public class GenericSessionServiceImpl implements GenericSessionService {

    private final Timeout timeout;

    private final StartProcessingFactory startProcessingFactory;

    private final NextProcessingFactory nextProcessingFactory;

    private final FinishProcessingFactory finishProcessingFactory;

    private final ResponseProcessor responseProcessor;

    private final SecurityUtils securityUtils;

    @Override
    public SessionData start(@NonNull final Long schemeId) {
        StartProcessingService startProcessingService ;
        if (securityUtils.isLmsUser()) {
            startProcessingService = startProcessingFactory.getInstance("lms");
            log.debug("LMS session start processing");
        } else {
            startProcessingService = startProcessingFactory.getInstance("non-lms");
            log.debug("Non-LMS session start processing");
        }
        return startProcessingService.start(schemeId);
    }

    @Override
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        this.forceCancelSuspended(sessionData);
        NextProcessingService nextProcessingService;
        if (sessionData.isDynamicSession()) {
            nextProcessingService = nextProcessingFactory.getInstance("dynamic");
            log.debug("Dynamic session next processing");
        } else {
            nextProcessingService = nextProcessingFactory.getInstance("basic");
            log.debug("Basic (static) session next processing");
        }
        BatchOutDto next = nextProcessingService.next(batchInDto, sessionData);
        // Control session time-out at the end,
        // so that to be able to apply penalties for overdue questions
        timeout.controlSessionTimeout();
        return next;
    }

    @Override
    public BatchOutDto current(@NonNull final SessionData sessionData) {
        timeout.controlSessionTimeout();
        this.forceCancelSuspended(sessionData);
        TimingService.synchronizeBatchOutDtoTimings(sessionData);
        BatchOutDto batchOutDto = sessionData.getCurrentBatch()
                .orElseThrow(() -> new RuntimeException("Current batch not found!"));
        return batchOutDto;
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
        this.forceCancelSuspended(sessionData);
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
        this.forceCancelSuspended(sessionData);
        Optional<BatchOutDto> currentBatch = sessionData.getCurrentBatch();
        // Accidental finish when timeouted without batch sending to server for evaluation
        if (currentBatch.isPresent() && !currentBatch.get().getQuestions().isEmpty()) {
            TimingService.updateTimeSpent(sessionData);
        }
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

    /**
     * For any request for new data, cancel suspended state of the session.
     * Front-end must ensure not to allow any API calls while session is suspended (paused or preserved),
     * by hiding the corresponding controls or making them disabled!
     * Otherwise it can go out of sync with the front-end state!
     * @param sessionData SessionData
     */
    private void forceCancelSuspended(SessionData sessionData) {
        if (sessionData.isSuspended()) {
            // Force to un-pause when requesting data
            TimingService.updateTimingsWhenContinued(sessionData);
        }
    }
}
