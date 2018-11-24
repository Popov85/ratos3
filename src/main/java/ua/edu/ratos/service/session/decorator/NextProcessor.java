package ua.edu.ratos.service.session.decorator;

import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;

public interface NextProcessor {

    BatchEvaluated getBatchEvaluated(BatchInDto batchInDto, SessionData sessionData);

    void updateComponentsSessionData(BatchEvaluated batchEvaluated, SessionData sessionData);

    BatchOutDto getBatchOutDto(BatchEvaluated batchEvaluated, SessionData sessionData);

    void updateSessionData(BatchOutDto batchOutDto, SessionData sessionData);

}
