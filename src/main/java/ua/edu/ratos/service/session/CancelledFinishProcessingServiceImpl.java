package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.ResultDomain;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.ResultOutDto;
import ua.edu.ratos.service.transformer.domain_to_dto.ResultDomainDtoTransformer;

@Service
public class CancelledFinishProcessingServiceImpl implements FinishProcessingService {

    private ResultBuilder resultBuilder;

    private AsyncFinishProcessingService asyncFinishProcessingService;

    private ResultDomainDtoTransformer resultDomainDtoTransformer;

    @Autowired
    public void setResultBuilder(ResultBuilder resultBuilder) {
        this.resultBuilder = resultBuilder;
    }

    @Autowired
    public void setAsyncFinishProcessingService(AsyncFinishProcessingService asyncFinishProcessingService) {
        this.asyncFinishProcessingService = asyncFinishProcessingService;
    }

    @Autowired
    @Qualifier("cancelled")
    public void setResultDomainDtoTransformer(ResultDomainDtoTransformer resultDomainDtoTransformer) {
        this.resultDomainDtoTransformer = resultDomainDtoTransformer;
    }

    @Override
    public ResultOutDto finish(@NonNull final SessionData sessionData) {
        boolean timeOuted = !sessionData.hasMoreTime();
        ResultDomain resultDomain = resultBuilder.build(sessionData, timeOuted, true);
        // Do regular async db finish operations, if enabled for cancelled sessions
        asyncFinishProcessingService.saveResults(resultDomain, sessionData);
        return resultDomainDtoTransformer.toDto(resultDomain);
    }

    @Override
    public String type() {
        return "cancelled";
    }
}
