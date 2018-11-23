package ua.edu.ratos.service.session;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.BatchEvaluated;
import ua.edu.ratos.service.session.domain.ProgressData;
import ua.edu.ratos.service.session.domain.ResponseEvaluated;
import ua.edu.ratos.service.session.domain.SessionData;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressDataService {

    /**
     * Create a new ProgressData object based on previous state kept in SessionData
     * Then the old one in SessionData is replaced by this new created object
     * @param sessionData
     * @param batchEvaluated
     */
    public void update(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
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
     * @param sessionData
     * @return
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
     * Get current score for intermediary result
     * @param sessionData
     * @return
     */
    public double getCurrentScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getScore();
        final int quantity = sessionData.getQuestions().size();
        return (score*100d)/quantity;
    }

    /**
     * Get current effective score for intermediary result
     * @param sessionData
     * @return
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
