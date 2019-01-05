package ua.edu.ratos.service.session.decorator;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.TimingService;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
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
    public BatchEvaluated getBatchEvaluated(@NonNull final BatchInDto batchInDto, @NonNull final SessionData sessionData) {
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
    public void updateComponentsSessionData(@NonNull final BatchEvaluated batchEvaluated, @NonNull final SessionData sessionData) {
        nextProcessor.updateComponentsSessionData(batchEvaluated, sessionData);
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
