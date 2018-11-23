package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.scheme.SchemeService;
import ua.edu.ratos.service.session.domain.Result;
import ua.edu.ratos.service.session.dto.*;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.domain.batch.BatchIn;
import ua.edu.ratos.service.session.domain.batch.BatchOut;

import java.util.List;


@Slf4j
@Service
public class GenericSessionServiceImpl implements GenericSessionService {

    private static final String NOT_AVAILABLE = "Requested scheme is not available now";

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SessionDataBuilder sessionDataBuilder;

    @Autowired
    private BatchBuilder batchBuilder;

    @Autowired
    private EvaluatingService evaluatingService;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private TimingService timingService;

    @Autowired
    private PyramidProcessorService pyramidProcessorService;

    @Autowired
    private ResultBuilder resultBuilder;

    @Autowired
    private ResultDtoBuilder resultDtoBuilder;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultDetailsService resultDetailsService;

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private MetaDataService metaDataService;

    @Override
    @TrackTime
    public SessionData start(@NonNull final String key, @NonNull final Long userId, @NonNull final Long schemeId) {
        // Load the requested Scheme
        final Scheme scheme = schemeService.findByIdForSession(schemeId);
        if (scheme==null || !scheme.isActive() || !scheme.isCompleted() || scheme.isDeleted())
            throw new IllegalStateException(NOT_AVAILABLE);

        // Build SessionData
        final SessionData sessionData = sessionDataBuilder.build(key, userId, scheme);

        // Build first BatchOut
        final BatchOut batchOut = batchBuilder.build(sessionData);

        // Update SessionData
        sessionDataService.update(sessionData, batchOut);

        log.debug("SessionData is built :: {}", sessionData);
        return sessionData;
    }

    /**
     * Front-end must ensure to call this method only if there is some non-null BatchOuts left
     * Algorithm:
     * 1. Timing control: back-end check if the Session or BatchOut is time-outed;
     *    if time-outed, client script should initiate the finish request with the same parameters!
     * 2. Evaluating if there is smth. to evaluate (non-empty BatchOut)
     *   (if empty BatchIn, then all non-skipped questions consider to be incorrectly answered)
     * 3. Return BatchOut
     * @param batchIn currentBatch with user's provided responses
     * @param sessionData
     * @return next batch for user
     */
    @Override
    @TrackTime
    public BatchOut next(@NonNull final BatchIn batchIn, @NonNull SessionData sessionData) {
        // Consider the client script to initiate request after either session or batch timeout, call finish then
        timingService.control(sessionData.getSessionTimeout(), sessionData.getCurrentBatchTimeOut());
        // Build next BatchOut
        BatchOut batchOut;
        // If current BatchOut contains smth. (we check because in case of skipping we remove elements from it)
        if (!sessionData.getCurrentBatch().getBatch().isEmpty()) {

            final BatchEvaluated batchEvaluated = evaluatingService.getBatchEvaluated(batchIn, sessionData);

            // Updates
            // update ProgressData
            progressDataService.update(sessionData, batchEvaluated);
            // update incorrect in MetaData if any
            List<Long> incorrectResponseIds = batchEvaluated.getIncorrectResponseIds();
            if (!incorrectResponseIds.isEmpty())
                metaDataService.createOrUpdateIncorrect(sessionData, incorrectResponseIds);

            // Pyramid
            boolean pyramid = sessionData.getScheme().getMode().isPyramid();
            if (pyramid) { // Process Pyramid mode (only if enabled)
                pyramidProcessorService.process(sessionData, batchEvaluated);
            }

            batchOut = batchBuilder.build(sessionData, batchEvaluated);
        } else {// All questions were skipped: nothing to evaluate
            // Build BatchOut with no batchEvaluated
            batchOut = batchBuilder.build(sessionData);
        }
        // update SessionData
        sessionDataService.update(sessionData, batchOut);
        return batchOut;
    }

    /**
     * 1. Check for time-out (if not time-outed call)
     * 2. Evaluate
     * 3. Build Result
     * 4-5. Save all the results
     * 6. Build ResultOutDto and return it
     * @param batchIn last BatchIn of questions
     * @param sessionData
     * @param timeOuted
     * @return ResultOutDto
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
        evaluatingService.getBatchEvaluated(batchIn, sessionData);
        // 3. Build and return Result
        final Result result = resultBuilder.build(sessionData);
        // 3.1 Reset currentBatch related fields
        if (!timeOuted) sessionDataService.finalize(sessionData);
        // 4. Save results to DB {result, result_theme}
        Long resultId = resultService.save(sessionData, result, timeOuted);
        // 5. Save serialised data {result_details}
        resultDetailsService.save(sessionData, resultId);
        // 6. Build resultDto based on settings and mode and Result object
        final ResultOutDto resultOutDto = resultDtoBuilder.build(sessionData, result);
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
        final Result result = resultBuilder.build(sessionData);
        return resultDtoBuilder.build(sessionData, result);
    }

}
