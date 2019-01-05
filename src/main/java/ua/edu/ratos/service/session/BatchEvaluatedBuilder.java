package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BatchEvaluatedBuilder {

    @Autowired
    private TimingService timingService;

    public BatchEvaluated build(@NonNull final Map<Long, ResponseEvaluated> responsesEvaluated,
                                @NonNull final List<Long> incorrectResponseIds,
                                @NonNull final SessionData sessionData) {
        // timing calculations
        final LocalDateTime currentBatchIssued = sessionData.getCurrentBatchIssued();
        final long timeSpent = timingService.getTimeSpent(currentBatchIssued);

        return new BatchEvaluated(responsesEvaluated, incorrectResponseIds, timeSpent);
    }
}
