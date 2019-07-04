package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProgressData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Total sum score without any bounties and penalties;
     * if so far user answered 10 out of 20 question, {100, 0, 100, 50, 100, 0, 0, 100, 100, 100}, then
     * this value would be 6.5 (sum of each score divided by 100);
     * This score is returned for intermediary result building.
     * (User actually sees his gross result that can bee corrected in the end due to buties and penalities)
     * This score is never >100.
     */
    private double score;

    /**
     * Total sum score with possible bounties (level 2/3) and penalties (batch/session time-out);
     * This score is returned at the end of a learning session.
     * This score can be > 100 if provided 100% correct responses with at least one of level >1;
     * Anyway return MAX = 100, so that not to confuse a user.
     */
    private double actualScore;

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
