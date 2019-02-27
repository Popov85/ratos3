package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProgressData {
    /**
     * Total sum score;
     * if so far user answered 10 out of 20 question, {100, 0, 100, 50, 100, 0, 0, 100, 100, 100}, then
     * this value would be 6.5 (sum of each score divided by 100);
     * Usage: for final and intermediary result building.
     */
    private double score;

    /**
     * How much of the total job is currently done in this session;
     * If so far user answered 3 out of 10 questions, then
     * this value would be 3/10 = 0.3 or 30%
     */
    private double progress;
    /**
     * Track time spent anyways, regardless settings
     */
    private long timeSpent;
    /**
     * List of evaluated batches
     */
    private List<BatchEvaluated> batchesEvaluated = new ArrayList<>();
}
