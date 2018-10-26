package ua.edu.ratos.service.session.domain;

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
     * this values would be 6.5 (sum of each score divided by 100)
     * Usage: for final and intermediary result building
     */
    private double score;
    /**
     * Track time spent anyways, regardless settings
     */
    private long timeSpent;
    /**
     * List of evaluated batches
     */
    private List<BatchEvaluated> batchesEvaluated = new ArrayList<>();
}
