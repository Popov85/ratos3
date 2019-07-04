package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Usage: when settings require to return the result of evaluation after each BatchInDto
 * For educational session only
 */
@Getter
@ToString
public class PreviousBatchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Total accumulative score;
     * if so far user answered 10 out of 20 question
     * and all 10 are answered correctly, then
     * this values would be 50.00 (%)
     * Usage: for immediate feedback after BatchInDto evaluation
     */
    private final double currentScore;
    /**
     * Total effective score
     * Signifies how accurate were answers so far;
     * if so far user answered 10 out of 20 question
     * and all 10 are answered correctly, then
     * this values would be 100.00 (%);
     * Usage: for immediate feedback after BatchInDto evaluation
     */
    private final double effectiveScore;

    /**
     * Map containing the evaluated score for each response;
     * Skipped questions are omitted
     */
    private final BatchEvaluated batchEvaluated;

    @JsonCreator
    public PreviousBatchResult(@JsonProperty("currentScore") double currentScore,
                               @JsonProperty("effectiveScore") double effectiveScore,
                               @JsonProperty("batchEvaluated") BatchEvaluated batchEvaluated) {
        this.currentScore = currentScore;
        this.effectiveScore = effectiveScore;
        this.batchEvaluated = batchEvaluated;
    }
}
