package ua.edu.ratos.service.session.decorator;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

@Service
public class NextProcessorTemplate {

    @Autowired
    private TimeDecorator timeDecorator;

    /**
     * Basically Spring does this chain:
     * TimeDecorator timeDecorator = new TimeDecorator(new SkipDecorator(new PyramidDecorator(new BasicNextProcessor())));
     * @param batchInDto
     * @param sessionData
     * @return batch out
     */
    public BatchOutDto process(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {

        BatchEvaluated batchEvaluated = timeDecorator.getBatchEvaluated(batchInDto, sessionData);
        timeDecorator.updateComponentsSessionData(batchEvaluated, sessionData);
        BatchOutDto batchOutDto = timeDecorator.getBatchOutDto(batchEvaluated, sessionData);
        timeDecorator.updateSessionData(batchOutDto, sessionData);

        return batchOutDto;
    }
}
