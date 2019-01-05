package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.config.TrackTime;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.ResultDetailsService;
import ua.edu.ratos.service.ResultService;
import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.grading.SchemeService;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import java.util.List;

@Slf4j
@Service
public class GenericSessionServiceImpl implements GenericSessionService {

    static final String NOT_AVAILABLE = "Requested scheme is not available now";
    static final String NOT_AVAILABLE_OUTSIDE_LMS = "Requested scheme is not available outside LMS";
    static final String NOT_AVAILABLE_FOR_USER = "Requested scheme is not available for this user";

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AppProperties appProperties;

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
    private PyramidService pyramidService;

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
    public SessionData start(@NonNull final StartData startData) {
        // Load the requested Scheme and build SessionData object
        final Scheme scheme = schemeService.findByIdForSession(startData.getSchemeId());
        if (scheme==null || !scheme.isActive() || !scheme.isCompleted() || scheme.isDeleted())
            throw new IllegalStateException(NOT_AVAILABLE);
        if (scheme.isLmsOnly())
            throw new IllegalStateException(NOT_AVAILABLE_OUTSIDE_LMS);
        if (appProperties.getSession().isInclude_groups()) {
            if (!availabilityService.isSchemeAvailable(scheme, startData.getUserId())) {
                throw new IllegalStateException(NOT_AVAILABLE_FOR_USER);
            }
        }
        log.debug("Found available scheme ID = {}", scheme.getSchemeId());
        final SessionData sessionData = sessionDataBuilder.build(startData.getKey(), startData.getUserId(), scheme);
        // Build first BatchOutDto
        final BatchOutDto batchOutDto = batchBuilder.build(sessionData);
        // Update SessionData
        sessionDataService.update(sessionData, batchOutDto);
        log.debug("SessionData is built = {}", sessionData);
        return sessionData;
    }


    /**
     * Does all processing of incoming request
     * @param batchInDto currentBatch with user's provided answers
     * @param sessionData
     * @return BatchOutDto
     */
    @Override
    @TrackTime
    public BatchOutDto next(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        // Consider Decorator pattern
        // @Autowire NextProcessorTemplate nextProcessorTemplate
        /* BatchOutDto batchOut = nextProcessorTemplate.process(batchInDto, sessionData);*/

        // Consider the client script to initiate finish request after either batch timeout or session timeout
        timingService.control(sessionData.getSessionTimeout(), sessionData.getCurrentBatchTimeOut());


        // Build next BatchOutDto
        BatchOutDto batchOutDto;
        // If current BatchOutDto contains smth. (we check because in case of skipping we remove elements from it)
        if (!sessionData.getCurrentBatch().isEmpty()) {

            final BatchEvaluated batchEvaluated = evaluatingService.getBatchEvaluated(batchInDto, sessionData);

            // update ProgressData
            progressDataService.update(sessionData, batchEvaluated);
            // update incorrect in MetaData if any
            List<Long> incorrectResponseIds = batchEvaluated.getIncorrectResponseIds();
            if (!incorrectResponseIds.isEmpty())
                metaDataService.createOrUpdateIncorrect(sessionData, incorrectResponseIds);

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


    /**
     * Front-end calls this method as soon as
     *   1) method next() returns a batch with empty questions list {},
     *      which means that there are no more questions left in the current session
     *      OR
     *   2) method next() throws time-out exception
     *      OR
     *   3) Front-end timer decides that time is over and launches finish request
     * @param sessionData
     * @return result object on the whole session
     */
    @Override
    @TrackTime
    @Transactional
    public ResultOutDto finish(@NonNull SessionData sessionData) {
        boolean timeOuted = !sessionData.hasMoreTime();
        if (sessionData.hasMoreQuestions() && !timeOuted)
            throw new IllegalStateException("Corrupt finish request, there are still some questions and time to answer!");
        // 1. Build Result object
        final ResultDomain resultDomain = resultBuilder.build(sessionData);
        // 2. Reset currentBatch related fields to null
        if (!timeOuted) sessionDataService.finalize(sessionData);
        // 3.  Save results to DB, optional LMS include
        Long resultId = resultService.save(sessionData, resultDomain, timeOuted);
        // 4. Save result details to DB
        resultDetailsService.save(sessionData, resultId);
        // 5. Build resultDto based on settings, mode and calculated Result object
        log.debug("Result :: {}", resultDomain);
        return resultDtoBuilder.build(sessionData, resultDomain);
    }

    /**
     * If cancelled by user, nothing is gonna be saved into DB
     * @param sessionData
     * @return just return the current result
     */
    @Override
    @TrackTime
    public ResultOutDto cancel(@NonNull SessionData sessionData) {
        final ResultDomain resultDomain = resultBuilder.build(sessionData);
        return resultDtoBuilder.build(sessionData, resultDomain);
    }

}
