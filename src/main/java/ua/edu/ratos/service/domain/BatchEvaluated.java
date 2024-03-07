package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class BatchEvaluated implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<ResponseEvaluated> responsesEvaluated;
    /**
     * TimeSpent is calculated approximately based on time between the batch network round trip
     */
    private final long timeSpent;
    /**
     * Whether of not this batch has been time-outed.
     */
    private final boolean timeouted;

    @JsonCreator
    public BatchEvaluated(@JsonProperty("responsesEvaluated") @NonNull final List<ResponseEvaluated> responsesEvaluated,
                          @JsonProperty("timeSpent") long timeSpent,
                          @JsonProperty("timeOuted") boolean timeouted) {
        this.responsesEvaluated = responsesEvaluated;
        this.timeSpent = timeSpent;
        this.timeouted = timeouted;
    }

    @JsonIgnore
    /**
     * Finds a list of incorrectly answered questions in the map of evaluated responses;
     * Used to shift incorrect-s in pyramid mode,
     * Also to update statistics of incorrect-s
     * @param responseEvaluated map of ResponseEvaluated
     * @return list of incorrectly answered question IDs
     */
    public List<Long> getIncorrectResponseIds() {
        return this.getResponsesEvaluated()
                .stream()
                .filter(q -> q.getScore() < 100)
                .map(ResponseEvaluated::getQuestionId)
                .collect(Collectors.toList());
    }
}
