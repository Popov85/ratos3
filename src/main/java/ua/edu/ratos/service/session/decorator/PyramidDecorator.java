package ua.edu.ratos.service.session.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.PyramidService;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;

@Service
@Qualifier("pyramid")
public class PyramidDecorator extends NextProcessorDecorator {

    @Autowired
    private PyramidService pyramidService;

    public PyramidDecorator(@Qualifier("basic") NextProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public BatchEvaluated getBatchEvaluated(BatchInDto batchInDto, SessionData sessionData) {
        return nextProcessor.getBatchEvaluated(batchInDto, sessionData);
    }

    @Override
    public void updateComponentsSessionData(BatchEvaluated batchEvaluated, SessionData sessionData) {
        nextProcessor.updateComponentsSessionData(batchEvaluated, sessionData);
        // Pyramid mode processing if enabled
        if (sessionData.getScheme().getMode().isPyramid()) {
            //PyramidService pyramidService = new PyramidService();
            pyramidService.process(sessionData, batchEvaluated);
        }
    }

    @Override
    public BatchOutDto getBatchOutDto(BatchEvaluated batchEvaluated, SessionData sessionData) {
        return nextProcessor.getBatchOutDto(batchEvaluated, sessionData);
    }

    @Override
    public void updateSessionData(BatchOutDto batchOutDto, SessionData sessionData) {
        nextProcessor.updateSessionData(batchOutDto, sessionData);
    }
}
