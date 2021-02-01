package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.transformer.ResultTransformer;

@Slf4j
@Service
public class RegularFinishProcessingServiceImpl implements FinishProcessingService {

    private final Timeout timeout;

    private final SessionDataService sessionDataService;

    private final ResultBuilder resultBuilder;

    private final AsyncFinishProcessingService asyncFinishProcessingService;

    private final ResultTransformer resultTransformer;

    public RegularFinishProcessingServiceImpl(Timeout timeout,
                                              SessionDataService sessionDataService,
                                              ResultBuilder resultBuilder,
                                              AsyncFinishProcessingService asyncFinishProcessingService,
                                              @Qualifier("regular") ResultTransformer resultTransformer) {
        this.timeout = timeout;
        this.sessionDataService = sessionDataService;
        this.resultBuilder = resultBuilder;
        this.asyncFinishProcessingService = asyncFinishProcessingService;
        this.resultTransformer = resultTransformer;
    }

    @Override
    public ResultOutDto finish(@NonNull final SessionData sessionData) {
        boolean timeOuted = timeout.isSessionTimeout();
        if (sessionData.hasMoreQuestions() && !timeOuted)
            throw new IllegalStateException(CORRUPT_FINISH_REQUEST
                    +" schemeId = "+sessionData.getSchemeDomain().getSchemeId()+" userId = "+sessionData.getUserId());
        // Reset currentBatch related fields to null
        if (!timeOuted) sessionDataService.finalize(sessionData);
        // Build result object + gamification check
        ResultDomain resultDomain = resultBuilder.build(sessionData, timeOuted, false);
        // Do regular async db finish operations + gamification processing
        asyncFinishProcessingService.saveResults(resultDomain, sessionData);
        return resultTransformer.toDto(resultDomain);
    }

    @Override
    public String name() {
        return "regular";
    }
}
