package ua.edu.ratos.service.session;

import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

@UtilityClass
public class TimingService {

    public long getTimeSpent(LocalDateTime currentBatchIssued) {
        return SECONDS.between(currentBatchIssued, LocalDateTime.now());
    }

    public boolean isUnlimited(LocalDateTime businessTimeout) {
        return businessTimeout.isEqual(LocalDateTime.MAX);
    }

    public boolean isLimited(LocalDateTime businessTimeout) {
        return !businessTimeout.isEqual(LocalDateTime.MAX);
    }

    public boolean isLimited(long perQuestionTimeLimit) {
        return perQuestionTimeLimit > 0;
    }

    public long getQuestionTimeOut(int secondsPerQuestion, boolean strictTimeControlPerQuestion) {
        if (secondsPerQuestion > 0 && strictTimeControlPerQuestion) return secondsPerQuestion;
        return -1;
    }

    public LocalDateTime getSessionTimeOut(int totalQuestions, int secondsPerQuestion) {
        if (secondsPerQuestion <= 0) {
            // Not limited in time -> strict per question time control is not possible
            return LocalDateTime.MAX;
        } else {
            long totalSeconds = secondsPerQuestion * totalQuestions;
            return getExpireTime(totalSeconds);
        }
    }

    private LocalDateTime getExpireTime(long totalSeconds) {
        return LocalDateTime.now().plusSeconds(totalSeconds);
    }
}
