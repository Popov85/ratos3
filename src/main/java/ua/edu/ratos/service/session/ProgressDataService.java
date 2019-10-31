package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.domain.BatchEvaluated;
import ua.edu.ratos.service.domain.ProgressData;
import ua.edu.ratos.service.domain.ResponseEvaluated;
import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ProgressDataService {

    private final EvaluatorPostProcessor evaluatorPostProcessor;

    /**
     * Creates a new ProgressData object based on previous state kept in SessionData
     * Then the old one in SessionData is replaced by this new created object
     * @param sessionData SessionData object associated with the current http-session
     * @param batchEvaluated previously evaluated batch, the source to update ProgressData object
     */
    public void update(@NonNull final SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        ProgressData updatedProgressData = new ProgressData();
        final ProgressData currentProgressData = sessionData.getProgressData();
        // New score
        final double updatedScore = getUpdatedScore(sessionData, batchEvaluated);
        updatedProgressData.setScore(updatedScore);
        // New actual score (+bounty, -penalty)
        final double updatedActualScore = getUpdatedActualScore(sessionData, batchEvaluated);
        updatedProgressData.setActualScore(updatedActualScore);
        // New time spent
        final long updatedTime = getUpdatedTime(sessionData, batchEvaluated);
        updatedProgressData.setTimeSpent(updatedTime);
        // New progress state
        updatedProgressData.setProgress(getProgress(sessionData));

        final List<BatchEvaluated> batchesEvaluated =
                new ArrayList<>(currentProgressData.getBatchesEvaluated());
        batchesEvaluated.add(batchEvaluated);
        updatedProgressData.setBatchesEvaluated(batchesEvaluated);

        // set SessionData
        sessionData.setProgressData(updatedProgressData);
    }


    /**
     * Converts List<BatchEvaluated> to List<ResponseEvaluated>
     * @param sessionData SessionData object associated with the current http-session
     * @return a collection of evaluated responses
     */
    public List<ResponseEvaluated> toResponseEvaluated(@NonNull final SessionData sessionData) {
        final List<BatchEvaluated> batchesEvaluated = sessionData.getProgressData().getBatchesEvaluated();
        List<ResponseEvaluated> result = new ArrayList<>();
        batchesEvaluated.forEach(batchEvaluated ->
                batchEvaluated.getResponsesEvaluated()
                        .forEach(responseEvaluated -> result.add(responseEvaluated)));
        return result;
    }


    /**
     * Calculate the current score (without bounties and penalties) in percent
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return the current score
     */
    public double getCurrentScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getScore();
        final int quantity = sessionData.getSequence().size();
        return (score*100d)/quantity;
    }

    /**
     * Calculate the current actual score (with bounties and penalties) for the final result (in percent)
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return the current actual score for final result
     */
    public double getCurrentActualScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getActualScore();
        if (score==0) return 0d;
        final int quantity = sessionData.getSequence().size();
        return (score*100d)/quantity;
    }

    /**
     * Calculate current effective score for intermediary result (in percent)
     * Effective score shows the effectiveness of answers irrespectively to the total amount of questions
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return the current effective score for intermediary result
     */
    public double getEffectiveActualScore(@NonNull final SessionData sessionData) {
        final double score = sessionData.getProgressData().getActualScore();
        if (score==0) return 0d;
        final int index = sessionData.getCurrentIndex();
        return (score*100d)/index;
    }

    /**
     * Calculate the percentage of job done in the current session;
     * @param sessionData SessionData object associated with the current http(s)-session
     * @return progress in % (0,33)
     */
    public double getProgress(@NonNull final SessionData sessionData) {
        int currentIndex = sessionData.getCurrentIndex();
        if (currentIndex ==0) return 0d;
        int total = sessionData.getSequence().size();
        return ((currentIndex+0.0d)/total);
    }

    private double getUpdatedScore(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        double currentScore = sessionData.getProgressData().getScore();
        final double sum = batchEvaluated.getResponsesEvaluated()
                .stream()
                .mapToDouble(r -> r.getScore() / 100d)
                .sum();
        return currentScore+sum;
    }

    private double getUpdatedActualScore(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        Map<Long, QuestionSessionOutDto> batchMap = sessionData.getCurrentBatch().get().getBatchMap();
        double currentScore = sessionData.getProgressData().getActualScore();
        // apply bounty for higher level if any
        float l2 = sessionData.getSchemeDomain().getSettingsDomain().getLevel2Coefficient();
        float l3 = sessionData.getSchemeDomain().getSettingsDomain().getLevel3Coefficient();
        final double sum = batchEvaluated.getResponsesEvaluated()
                .stream()
                .mapToDouble(r -> {
                    byte level = batchMap.get(r.getQuestionId()).getLevel();
                    return evaluatorPostProcessor.applyBounty(r.getScore(), level, l2, l3) / 100d;
                }).sum();
        // apply penalty to the whole batch if any
        return currentScore+(batchEvaluated.isTimeouted() ? evaluatorPostProcessor.applyPenalty(sum) : sum);
    }

    private long getUpdatedTime(@NonNull SessionData sessionData, @NonNull final BatchEvaluated batchEvaluated) {
        final long currentTime = sessionData.getProgressData().getTimeSpent();
        final long addTime = batchEvaluated.getTimeSpent();
        return currentTime+addTime;
    }

}
