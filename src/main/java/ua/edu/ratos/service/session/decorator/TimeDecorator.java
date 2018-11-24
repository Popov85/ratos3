package ua.edu.ratos.service.session.decorator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.TimingService;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;
import ua.edu.ratos.service.session.dto.batch.BatchOutDto;
import ua.edu.ratos.web.exception.RunOutOfTimeException;

@Service
@Qualifier("time")
public class TimeDecorator extends NextProcessorDecorator {
    /**
     * Add 3 or more seconds (experimental value) to compensate the network round trips time;
     * Normally, the  client script would initiate the request to server in case of time-out either session or batch;
     * In turn, server also must check the timeout, but adds 3 or more sec to compensate the network traverse time
     */
    private static final long ADD_SECONDS = 3;

    @Autowired
    private TimingService timingService;

    public TimeDecorator(@Qualifier("skip") NextProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public BatchEvaluated getBatchEvaluated(BatchInDto batchInDto, SessionData sessionData) {
        //TimingService timingService = new TimingService();
        if (timingService.isLimited(sessionData.getSessionTimeout())) {
            if (timingService.isExpired(sessionData.getSessionTimeout().plusSeconds(ADD_SECONDS))) {
                // session expired
                throw new RunOutOfTimeException(true, false);
            }
            if (timingService.isExpired(sessionData.getCurrentBatchTimeOut().plusSeconds(ADD_SECONDS))) {
                // batch expired
                throw new RunOutOfTimeException(false, true);
            }
        }
        return nextProcessor.getBatchEvaluated(batchInDto, sessionData);
    }

    @Override
    public void updateComponentsSessionData(BatchEvaluated batchEvaluated, SessionData sessionData) {
        nextProcessor.updateComponentsSessionData(batchEvaluated, sessionData);
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
