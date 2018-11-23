package ua.edu.ratos.service.session.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Usage: when settings require to return the result of evaluation after each BatchIn
 * For educational dto only
 */
@Getter
@ToString
@AllArgsConstructor
public class PreviousBatchResult {
    /**
     * Total accumulative score;
     * if so far user answered 10 out of 20 question
     * and all 10 are answered correctly, then
     * this values would be 50.00 (%)
     * Usage: for immediate feedback after BatchIn evaluation
     */
    private final double currentScore;
    /**
     * Total effective score
     * Signifies how accurate were answers so far;
     * if so far user answered 10 out of 20 question
     * and all 10 are answered correctly, then
     * this values would be 100.00 (%);
     * Usage: for immediate feedback after BatchIn evaluation
     */
    private final double effectiveScore;

    /**
     * Map containing the evaluated score for each response;
     * Skipped questions are omitted
     */
    private final BatchEvaluated batchEvaluated;

}
