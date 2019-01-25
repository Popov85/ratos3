package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;
import ua.edu.ratos.service.domain.SessionData;
import java.time.LocalDateTime;

@Service
public class SessionDataService {

    @Autowired
    private TimingService timingService;

    /**
     * Update the state of the current dto based on a new batchOutDto provided
     * @param sessionData
     * @param batchOutDto
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final BatchOutDto batchOutDto) {
        int shift = batchOutDto.getBatch().size();
        int newIndex = sessionData.getCurrentIndex() + shift;
        sessionData.setCurrentIndex(newIndex);
        sessionData.setCurrentBatch(batchOutDto);
        sessionData.setCurrentBatchIssued(LocalDateTime.now());
        // Update batch timing if needed;
        final long perQuestionTimeLimit = sessionData.getPerQuestionTimeLimit();
        if (timingService.isLimited(perQuestionTimeLimit)) {
            sessionData.setCurrentBatchTimeOut(LocalDateTime.now().plusSeconds(batchOutDto.getBatchTimeLimit()));
        }
    }

    /**
     * Reset currentBach related fields in case of normal finish
     * @param sessionData
     */
    public void finalize(@NonNull final SessionData sessionData) {
        sessionData.setCurrentBatch(null);
        sessionData.setCurrentBatchTimeOut(null);
        sessionData.setCurrentBatchIssued(null);
    }
}
