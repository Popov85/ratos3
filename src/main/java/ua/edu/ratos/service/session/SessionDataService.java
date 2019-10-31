package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.domain.SettingsDomain;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

import java.time.LocalDateTime;

@Service
public class SessionDataService {
    /**
     * Update the state of the current session based on
     * a new batchOutDto issued: index, currentBatch, currentBatchIssued, currentBatchTimeOut
     * @param sessionData
     * @param batchOutDto
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final BatchOutDto batchOutDto) {
        int shift = batchOutDto.getQuestions().size();
        int newIndex = sessionData.getCurrentIndex() + shift;
        sessionData.setCurrentIndex(newIndex);
        sessionData.setCurrentBatch(batchOutDto);
        sessionData.setCurrentBatchIssued(LocalDateTime.now());
        // Update batch timing if needed;
        if (sessionData.isBatchTimeLimited()) {
            SettingsDomain s = sessionData.getSchemeDomain().getSettingsDomain();
            final long perQuestionTimeLimit = s.getSecondsPerQuestion();
            final int questionsPerBatch = s.getQuestionsPerSheet();
            long totalBatchTimeout = questionsPerBatch * perQuestionTimeLimit;
            sessionData.setCurrentBatchTimeout(LocalDateTime.now().plusSeconds(totalBatchTimeout));
        }
    }

    /**
     * Reset currentBach related fields in case of normal save
     * @param sessionData
     */
    public void finalize(@NonNull final SessionData sessionData) {
        sessionData.setCurrentBatch(null);
        sessionData.setCurrentBatchTimeout(null);
        sessionData.setCurrentBatchIssued(null);
    }
}
