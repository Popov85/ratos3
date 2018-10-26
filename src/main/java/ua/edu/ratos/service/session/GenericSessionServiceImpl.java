package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.domain.entity.*;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.scheme.SchemeService;
import ua.edu.ratos.service.dto.session.*;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.ResultOut;
import ua.edu.ratos.service.session.domain.SessionData;
import java.util.Optional;


@Slf4j
@Service
public class GenericSessionServiceImpl implements GenericSessionService {

    private static final String NOT_AVAILABLE = "Requested scheme is not available now";

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SessionDataBuilderService sessionDataBuilderService;

    @Autowired
    private BatchBuilderService batchBuilderService;

    @Autowired
    private EvaluatingService evaluatingService;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private TimingService timingService;

    @Autowired
    private ResultBuilderService resultBuilderService;

    @Autowired
    private ResultDtoBuilderService resultDtoBuilderService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultDetailsService resultDetailsService;

    @Override
    @TrackTime
    public SessionData start(@NonNull final String key, @NonNull final Long userId, @NonNull final Long schemeId) {
        // Load the requested Scheme
        final Scheme scheme = schemeService.findByIdForSession(schemeId);
        if (scheme==null || !scheme.isActive() || !scheme.isCompleted() || scheme.isDeleted())
            throw new IllegalStateException(NOT_AVAILABLE);

        // Build SessionData
        final SessionData sessionData = sessionDataBuilderService.build(key, userId, scheme);

        // Build first BatchOut
        final BatchOut batchOut = batchBuilderService.build(sessionData);

        // Update SessionData
        sessionDataService.update(sessionData, batchOut);

        log.debug("SessionData is built :: {}", sessionData);
        return sessionData;
    }

    /**
     * 1. Timing control: back-end check if the Session or BatchOut is time-outed
     *    If time-outed, client script should initiate the finish request with the same parameters!
     * 2. Evaluating
     * 3. Return BatchOut
     * @param batchIn currentBatch with user's provided answers
     * @param sessionData
     * @return next batch for user
     */
    @Override
    @TrackTime
    public BatchOut next(@NonNull final BatchIn batchIn, @NonNull SessionData sessionData) {
        // Timing control
        // Consider the client script to initiate request after either session or batch timeout, call finish then
        timingService.control(sessionData.getSessionTimeout(), sessionData.getCurrentBatchTimeOut());

        // Evaluate
        final Optional<BatchEvaluated> batchEvaluated = evaluatingService.evaluate(batchIn, sessionData);

        // Build next BatchOut
        BatchOut batchOut;
        if (batchEvaluated.isPresent()) {
            batchOut = batchBuilderService.build(sessionData, batchEvaluated.get());
        } else {
            batchOut = batchBuilderService.build(sessionData);
        }
        // Update SessionData
        sessionDataService.update(sessionData, batchOut);
        return batchOut;
    }

    /**
     * 1. Check for time-out (if not time-outed call)
     * 2. Evaluate
     * 3. Build ResultOut
     * 4-5. Save all the results
     * 6. Build ResultOutDto and return it
     * @param batchIn last BatchIn of questions
     * @param sessionData
     * @param timeOuted
     * @return
     */
    @Override
    @TrackTime
    @Transactional
    public ResultOutDto finish(@NonNull final BatchIn batchIn, @NonNull SessionData sessionData, boolean timeOuted) {
        // 1. Check if the Session or BatchOut is time-outed, if so throw exception
        if (!timeOuted) {
            timingService.control(sessionData.getSessionTimeout(), sessionData.getCurrentBatchTimeOut());
        }
        // 2. Evaluate
        evaluatingService.evaluate(batchIn, sessionData);
        // 3. Build and return ResultOut
        final ResultOut result = resultBuilderService.build(sessionData);
        // 4. Save results to DB {result, result_details, result_theme}
        // 5. Save serialised data after calculating removal time
        resultDetailsService.save(sessionData, resultService.save(sessionData, result, timeOuted));
        // 5.1 TODO deal with Meta data: save complained and starred questions
        // 6. Build resultDto based on settings and mode and ResultOut object
        final ResultOutDto resultOutDto = resultDtoBuilderService.build(sessionData, result);
        // 7. Return resultDto
        return resultOutDto;
    }

    /**
     * If cancelled by user, nothing is gonna be saved into DB
     * @param sessionData
     * @return just return the current result
     */
    @Override
    @TrackTime
    public ResultOutDto cancel(@NonNull SessionData sessionData) {
        final ResultOut result = resultBuilderService.build(sessionData);
        return resultDtoBuilderService.build(sessionData, result);
    }

}
