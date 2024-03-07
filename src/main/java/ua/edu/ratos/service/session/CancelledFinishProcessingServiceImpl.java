package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.transformer.ResultTransformer;

@Service
public class CancelledFinishProcessingServiceImpl implements FinishProcessingService {

    private final Timeout timeout;

    private final ResultBuilder resultBuilder;

    private final AsyncFinishProcessingService asyncFinishProcessingService;

    private final ResultTransformer resultTransformer;

    public CancelledFinishProcessingServiceImpl(Timeout timeout,
                                                ResultBuilder resultBuilder,
                                                AsyncFinishProcessingService asyncFinishProcessingService,
                                                @Qualifier("cancelled") ResultTransformer resultTransformer) {
        this.timeout = timeout;
        this.resultBuilder = resultBuilder;
        this.asyncFinishProcessingService = asyncFinishProcessingService;
        this.resultTransformer = resultTransformer;
    }

    @Override
    public ResultOutDto finish(@NonNull final SessionData sessionData) {
        boolean timeOuted = timeout.isSessionTimeout();
        ResultDomain resultDomain = resultBuilder.build(sessionData, timeOuted, true);
        // Do regular async db finish operations, if enabled for cancelled sessions
        asyncFinishProcessingService.saveResults(resultDomain, sessionData);
        return resultTransformer.toDto(resultDomain);
    }

    @Override
    public String name() {
        return "cancelled";
    }
}
