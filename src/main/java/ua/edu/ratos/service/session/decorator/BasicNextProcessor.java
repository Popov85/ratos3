package ua.edu.ratos.service.session.decorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.*;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
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
    public BatchEvaluated getBatchEvaluated(BatchInDto batchInDto, SessionData sessionData) {
        //EvaluatingService evaluatingService = new EvaluatingService(); // Manually manage dependencies
        return evaluatingService.getBatchEvaluated(batchInDto, sessionData);
    }

    @Override
    public void updateComponentsSessionData(BatchEvaluated batchEvaluated, SessionData sessionData) {
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
    public BatchOutDto getBatchOutDto(BatchEvaluated batchEvaluated, SessionData sessionData) {
        //BatchBuilder batchBuilder = new BatchBuilder();
        if (!sessionData.isMoreQuestions()) {
            return BatchOutDto.buildEmpty();
        } else {
            return batchBuilder.build(sessionData, batchEvaluated);
        }
    }

    @Override
    public void updateSessionData(BatchOutDto batchOutDto, SessionData sessionData) {
        //SessionDataService sessionDataService = new SessionDataService();
        if (!batchOutDto.isEmpty()) sessionDataService.update(sessionData, batchOutDto);
        log.debug("Next batch :: {}", batchOutDto);
    }
}
