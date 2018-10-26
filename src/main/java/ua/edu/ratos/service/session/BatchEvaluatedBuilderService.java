package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.OptionsDto;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class BatchEvaluatedBuilderService {

    @Autowired
    private TimingService timingService;

    public BatchEvaluated build(@NonNull final Map<Long, ResponseEvaluated> responsesEvaluated,
                                @NonNull final Map<Long, OptionsDto> options, @NonNull final SessionData sessionData) {
        // timing calculations
        final LocalDateTime currentBatchIssued = sessionData.getCurrentBatchIssued();
        final long timeSpent = timingService.getTimeSpent(currentBatchIssued);

        return new BatchEvaluated(responsesEvaluated, options, timeSpent);
    }
}
