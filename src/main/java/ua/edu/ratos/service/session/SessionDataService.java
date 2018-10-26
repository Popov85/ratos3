package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.BatchOut;
import ua.edu.ratos.service.session.domain.ProgressData;
import ua.edu.ratos.service.session.domain.SessionData;

import java.time.LocalDateTime;

@Service
public class SessionDataService {

    @Autowired
    private TimingService timingService;

    /**
     * Update the state of the current session based on a new batchOut provided
     * @param sessionData
     * @param batchOut
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final BatchOut batchOut) {
        int newIndex = sessionData.getCurrentIndex()+batchOut.getBatch().size();
        sessionData.setCurrentIndex(newIndex);
        sessionData.setCurrentBatch(batchOut);
        sessionData.setCurrentBatchIssued(LocalDateTime.now());
        // Update batch timing if needed;
        final long perQuestionTimeLimit = sessionData.getPerQuestionTimeLimit();
        if (timingService.isLimited(perQuestionTimeLimit)) {
            sessionData.setCurrentBatchTimeOut(
                    LocalDateTime
                            .now()
                            .plusSeconds(batchOut.getBatchTimeControl()));
        }
    }

    /**
     * Update the state of ProgressData after evaluating BatchIn
     * @param sessionData
     * @param progressData
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final ProgressData progressData) {
        sessionData.setProgressData(progressData);
    }
}
