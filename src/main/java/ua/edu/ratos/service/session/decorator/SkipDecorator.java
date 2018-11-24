package ua.edu.ratos.service.session.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.BatchBuilder;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;

@Service
@Qualifier("skip")
public class SkipDecorator extends NextProcessorDecorator {

    @Autowired
    private BatchBuilder batchBuilder;

    public SkipDecorator(@Qualifier("pyramid") NextProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public BatchEvaluated getBatchEvaluated(BatchInDto batchInDto, SessionData sessionData) {
        if (!sessionData.getCurrentBatch().isEmpty()) {
            return nextProcessor.getBatchEvaluated(batchInDto, sessionData);
        } else {
            return null;
        }
    }

    @Override
    public void updateComponentsSessionData(BatchEvaluated batchEvaluated, SessionData sessionData) {
        if (batchEvaluated!=null) nextProcessor.updateComponentsSessionData(batchEvaluated, sessionData);
    }

    @Override
    public BatchOutDto getBatchOutDto(BatchEvaluated batchEvaluated, SessionData sessionData) {
        if (batchEvaluated!=null) {
            return nextProcessor.getBatchOutDto(batchEvaluated, sessionData);
        } else {
            if (!sessionData.isMoreQuestions()) {
                return BatchOutDto.buildEmpty();
            } else {
                // Build BatchOutDto with no batchEvaluated
                //BatchBuilder batchBuilder = new BatchBuilder();
                return batchBuilder.build(sessionData);
            }
        }
    }

    @Override
    public void updateSessionData(BatchOutDto batchOutDto, SessionData sessionData) {
        nextProcessor.updateSessionData(batchOutDto, sessionData);
    }

}
