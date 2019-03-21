package ua.edu.ratos.service.session;

import org.springframework.stereotype.Service;
import ua.edu.ratos.web.exception.RunOutOfTimeException;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class TimingService {
    /**
     * Add 3 or more seconds (experimental value) to compensate the network round trips time;
     * Normally, the  client script would initiate the request to server in case of time-out either session or batch;
     * In turn, server also must check the timeout, but adds 3 or more sec to compensate the network traverse time
     */
    private static final long ADD_SECONDS = 3;

    public LocalDateTime getExpireTime(long totalSeconds) {
        return LocalDateTime.now().plusSeconds(totalSeconds);
    }

    public long getTimeSpent(LocalDateTime currentBatchIssued) {
        return SECONDS.between(currentBatchIssued, LocalDateTime.now());
    }

    public boolean isExpired(LocalDateTime businessTimeout){
        return LocalDateTime.now().isAfter(businessTimeout);
    }

    public boolean isLimited(LocalDateTime sessionTimeout){
        return !sessionTimeout.isEqual(LocalDateTime.MAX);
    }

    public boolean isLimited(long perQuestionTimeLimit){
        return perQuestionTimeLimit>0;
    }


    public LocalDateTime getSessionTimeOut(int totalQuestions, int secondsPerQuestion) {
        if (secondsPerQuestion<=0) {
            // Not limited in time -> strict per question time control is not possible
            return LocalDateTime.MAX;
        } else {
            long totalSeconds = secondsPerQuestion*totalQuestions;
            return getExpireTime(totalSeconds);
        }
    }

    public long getQuestionTimeOut(int secondsPerQuestion, boolean strictTimeControlPerQuestion) {
        if (secondsPerQuestion>0 && strictTimeControlPerQuestion) return secondsPerQuestion;
        return -1;
    }

    public void control(LocalDateTime sessionTimeout, LocalDateTime batchTimeOut) {
        if (isLimited(sessionTimeout)) {

            if (isLimited(sessionTimeout) && isExpired(sessionTimeout.plusSeconds(ADD_SECONDS))) {
                // session expired, front-end initiates finish request with timeOuted param = true
                throw new RunOutOfTimeException(true, false);
            }
            if (isLimited(batchTimeOut) && isExpired(batchTimeOut.plusSeconds(ADD_SECONDS))) {
                // batch expired, front-end initiates finish request with timeOuted param = true
                // TODO: instead of throwing exception and finishing the whole session
                // consider an alternative behaviour: the whole current batch consider incorrect and issue next batch?
                throw new RunOutOfTimeException(false, true);
            }
        }
    }

}
