package ua.edu.ratos.service.session.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;

@Service
public class NextProcessorTemplate {

    @Autowired
    private TimeDecorator timeDecorator;

    /**
     * Basically Spring does this chain:
     * TimeDecorator timeDecorator = new TimeDecorator(new SkipDecorator(new PyramidDecorator(new BasicNextProcessor())));
     * @param batchInDto
     * @param sessionData
     * @return
     */
    public BatchOutDto process(BatchInDto batchInDto, SessionData sessionData) {

        BatchEvaluated batchEvaluated = timeDecorator.getBatchEvaluated(batchInDto, sessionData);
        timeDecorator.updateComponentsSessionData(batchEvaluated, sessionData);
        BatchOutDto batchOutDto = timeDecorator.getBatchOutDto(batchEvaluated, sessionData);
        timeDecorator.updateSessionData(batchOutDto, sessionData);

        return batchOutDto;
    }
}
