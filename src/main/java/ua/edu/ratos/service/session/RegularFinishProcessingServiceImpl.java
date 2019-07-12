package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.transformer.domain_to_dto.ResultDomainDtoTransformer;

@Slf4j
@Service
public class RegularFinishProcessingServiceImpl implements FinishProcessingService {

    @Autowired
    private Timeout timeout;

    private SessionDataService sessionDataService;

    private ResultBuilder resultBuilder;

    private AsyncFinishProcessingService asyncFinishProcessingService;

    private ResultDomainDtoTransformer resultDomainDtoTransformer;

    @Autowired
    public void setSessionDataService(SessionDataService sessionDataService) {
        this.sessionDataService = sessionDataService;
    }

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }

    @Autowired
    public void setAsyncFinishProcessingService(AsyncFinishProcessingService asyncFinishProcessingService) {
        this.asyncFinishProcessingService = asyncFinishProcessingService;
    }

    @Autowired
    @Qualifier("regular")
    public void setResultDomainDtoTransformer(ResultDomainDtoTransformer resultDomainDtoTransformer) {
        this.resultDomainDtoTransformer = resultDomainDtoTransformer;
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
        return resultDomainDtoTransformer.toDto(resultDomain);
    }

    @Override
    public String name() {
        return "regular";
    }
}
