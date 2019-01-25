package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.ProgressData;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressDataService {

    /**
     * Creates a new ProgressData object based on previous state kept in SessionData
     * Then the old one in SessionData is replaced by this new created object
     * @param sessionData SessionData object associated with the current http(s)-session
     * @param batchEvaluated previously evaluated batch, the source to update ProgressData object
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        ProgressData updatedProgressData = new ProgressData();
        final ProgressData currentProgressData = sessionData.getProgressData();
        final double updatedScore = getUpdatedScore(sessionData, batchEvaluated);
        updatedProgressData.setScore(updatedScore);
        final long updatedTime = getUpdatedTime(sessionData, batchEvaluated);
        updatedProgressData.setTimeSpent(updatedTime);
        final List<BatchEvaluated> batchesEvaluated =
                new ArrayList<>(currentProgressData.getBatchesEvaluated());
        batchesEvaluated.add(batchEvaluated);
        updatedProgressData.setBatchesEvaluated(batchesEvaluated);
        // set SessionData
        sessionData.setProgressData(updatedProgressData);
    }

    /**
     * Converts List<BatchEvaluated> to List<ResponseEvaluated>
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return a collection of evaluated responses
     */
    public List<ResponseEvaluated> toResponseEvaluated(@NonNull final SessionData sessionData) {
        final List<BatchEvaluated> batchesEvaluated = sessionData.getProgressData().getBatchesEvaluated();
        List<ResponseEvaluated> result = new ArrayList<>();
        batchesEvaluated.forEach(batchEvaluated ->
                batchEvaluated.getResponsesEvaluated()
                        .values()
                        .forEach(responseEvaluated -> result.add(responseEvaluated)));
        return result;
    }

    /**
     * Calculate the current score for intermediary result (in percent)
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return the current score for intermediary result
     */
    public double getCurrentScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getScore();
        final int quantity = sessionData.getQuestionDomains().size();
        return (score*100d)/quantity;
    }

    /**
     * Calculate current effective score for intermediary result (in percent)
     * Effective score shows the effectiveness of answers irrespectively to the total amount of questions
     * @param sessionData
     * @return the current effective score for intermediary result
     */
    public double getEffectiveScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getScore();
        final int index = sessionData.getCurrentIndex();
        return (score*100d)/index;
    }




    private double getUpdatedScore(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        double currentScore = sessionData.getProgressData().getScore();
        final double sum = batchEvaluated.getResponsesEvaluated()
                .values()
                .stream()
                .mapToDouble(r -> r.getScore() / 100d)
                .sum();
        return currentScore+sum;
    }

    private long getUpdatedTime(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        final long currentTime = sessionData.getProgressData().getTimeSpent();
        final long addTime = batchEvaluated.getTimeSpent();
        return currentTime+addTime;
    }

}
