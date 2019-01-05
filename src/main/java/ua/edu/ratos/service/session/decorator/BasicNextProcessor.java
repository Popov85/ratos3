package ua.edu.ratos.service.session.decorator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.*;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import java.util.List;

@Slf4j
@Service
@Qualifier("basic")
public class BasicNextProcessor implements NextProcessor {

    @Autowired
    private EvaluatingService evaluatingService;

    @Autowired
    private ProgressDataService progressDataService;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private BatchBuilder batchBuilder;

    @Override
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        //EvaluatingService evaluatingService = new EvaluatingService(); // Manually manage dependencies
        return evaluatingService.getBatchEvaluated(batchInDto, sessionData);
    }

    @Override
    public void updateComponentsSessionData(@NonNull final BatchEvaluated batchEvaluated, @NonNull final SessionData sessionData) {
        // update ProgressData
        //ProgressDataService progressDataService = new ProgressDataService();
        progressDataService.update(sessionData, batchEvaluated);
        // update incorrect in MetaData if any
        List<Long> incorrectResponseIds = batchEvaluated.getIncorrectResponseIds();
        if (!incorrectResponseIds.isEmpty()) {
            //MetaDataService metaDataService = new MetaDataService();
            metaDataService.createOrUpdateIncorrect(sessionData, incorrectResponseIds);
        }
    }

    @Override
    public BatchOutDto getBatchOutDto(@NonNull final BatchEvaluated batchEvaluated, @NonNull final SessionData sessionData) {
        //BatchBuilder batchBuilder = new BatchBuilder();
        if (!sessionData.hasMoreQuestions()) {
            return BatchOutDto.buildEmpty();
        } else {
            return batchBuilder.build(sessionData, batchEvaluated);
        }
    }

    @Override
    public void updateSessionData(@NonNull final BatchOutDto batchOutDto, @NonNull final SessionData sessionData) {
        //SessionDataService sessionDataService = new SessionDataService();
        if (!batchOutDto.isEmpty()) sessionDataService.update(sessionData, batchOutDto);
        log.debug("Next batch :: {}", batchOutDto);
    }
}
