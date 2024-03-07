package ua.edu.ratos.service.session;

import lombok.experimental.UtilityClass;
import ua.edu.ratos.service.domain.ProgressData;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchOutDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@UtilityClass
public class TimingService {

    /**
     * Calculates seconds between now and when the current batch was issued
     *
     * @param currentBatchIssued time when the current batch was issued
     * @return seconds
     */
    public long getTimeSpent(LocalDateTime currentBatchIssued) {
        return SECONDS.between(currentBatchIssued, LocalDateTime.now());
    }

    /**
     * Decides if there is any time limit to either session or batch
     *
     * @param businessTimeout business timeout
     * @return true - not limit, false - there is a limit
     */
    public boolean isUnlimited(LocalDateTime businessTimeout) {
        return businessTimeout.isEqual(LocalDateTime.MAX);
    }

    /**
     * For pauseable/preservable sessions we need to keep track of
     * remaining seconds till session  and batch timeout;
     * At stop time, it just calculates and sets the corresponding values inside SessionData
     *
     * @param sessionData
     */
    public void updateTimingsWhenPaused(final SessionData sessionData) {
        // If current session is time-limited
        synchronizeBatchOutDtoTimings(sessionData);
        sessionData.setSuspended(true);
    }

    /**
     * For pauseable/preservable sessions we need to keep track of
     * remaining seconds till session and batch timeout;
     * After proceeding, it just calculates and sets the corresponding timeouts inside SessionData
     *
     * @param sessionData
     */
    public void updateTimingsWhenContinued(final SessionData sessionData) {
        // If current session is time-limited
        if (sessionData.isSessionTimeLimited()) {
            BatchOutDto batchOutDto = sessionData.getCurrentBatch()
                    .orElseThrow(() -> new RuntimeException("Current batch not found!"));
            long remains = batchOutDto.getSessionExpiresInSec();
            sessionData.setSessionTimeout(LocalDateTime.now().plusSeconds(remains));
        }

        // If current session batch is time-limited
        if (sessionData.isBatchTimeLimited()) {
            BatchOutDto batchOutDto = sessionData.getCurrentBatch()
                    .orElseThrow(() -> new RuntimeException("Current batch not found!"));
            long remains = batchOutDto.getBatchExpiresInSec();
            sessionData.setCurrentBatchTimeout(LocalDateTime.now().plusSeconds(remains));
        }
        sessionData.setSuspended(false);
        sessionData.setCurrentBatchIssued(LocalDateTime.now());
    }


    /**
     * Updates only time spent in the following cases:
     * Accidental finish when timeouted without batch sending to server for evaluation
     *
     * @param sessionData
     */
    public void updateTimeSpent(final SessionData sessionData) {
        Optional<BatchOutDto> currentBatch = sessionData.getCurrentBatch();
        if (currentBatch.isPresent() && !currentBatch.get().getQuestions().isEmpty()) {
            ProgressData progressData = sessionData.getProgressData();
            long timeSpent = progressData.getTimeSpent();
            LocalDateTime currentBatchIssued = sessionData.getCurrentBatchIssued();
            progressData.setTimeSpent(timeSpent + TimingService.getTimeSpent(currentBatchIssued));
        }
    }

    /**
     * Updates timing in the current batch when it was requested again for some reason!
     *
     * @param sessionData
     */
    public void synchronizeBatchOutDtoTimings(final SessionData sessionData) {
        // If current session is time-limited
        if (sessionData.isSessionTimeLimited()) {
            LocalDateTime sessionTimeout = sessionData.getSessionTimeout();
            Duration duration = Duration.between(LocalDateTime.now(), sessionTimeout);
            long diff = duration.toMillis() / 1000;
            BatchOutDto batchOutDto = sessionData.getCurrentBatch()
                    .orElseThrow(() -> new RuntimeException("Current batch not found!"));
            batchOutDto.setSessionExpiresInSec(diff);
        }

        // If current session batch is time-limited
        if (sessionData.isBatchTimeLimited()) {
            LocalDateTime currentBatchTimeOut = sessionData.getCurrentBatchTimeout();
            Duration duration = Duration.between(LocalDateTime.now(), currentBatchTimeOut);
            long diff = duration.toMillis() / 1000;
            BatchOutDto batchOutDto = sessionData.getCurrentBatch()
                    .orElseThrow(() -> new RuntimeException("Current batch not found!"));
            batchOutDto.setBatchExpiresInSec(diff);
        }
    }
}
