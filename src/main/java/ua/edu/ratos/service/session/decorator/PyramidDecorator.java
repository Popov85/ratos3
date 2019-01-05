package ua.edu.ratos.service.session.decorator;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.PyramidService;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

@Service
@Qualifier("pyramid")
public class PyramidDecorator extends NextProcessorDecorator {

    @Autowired
    private PyramidService pyramidService;

    public PyramidDecorator(@Qualifier("basic") NextProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
        return nextProcessor.getBatchEvaluated(batchInDto, sessionData);
    }

    @Override
    public void updateComponentsSessionData(@NonNull final BatchEvaluated batchEvaluated, @NonNull final SessionData sessionData) {
        nextProcessor.updateComponentsSessionData(batchEvaluated, sessionData);
        // Pyramid modeDomain processing if enabled
        if (sessionData.getSchemeDomain().getModeDomain().isPyramid()) {
            //PyramidService pyramidService = new PyramidService();
            pyramidService.process(sessionData, batchEvaluated);
        }
    }

    @Override
    public BatchOutDto getBatchOutDto(@NonNull final BatchEvaluated batchEvaluated, @NonNull final SessionData sessionData) {
        return nextProcessor.getBatchOutDto(batchEvaluated, sessionData);
    }

    @Override
    public void updateSessionData(@NonNull final BatchOutDto batchOutDto, @NonNull final SessionData sessionData) {
        nextProcessor.updateSessionData(batchOutDto, sessionData);
    }
}
