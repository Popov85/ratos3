package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class BatchEvaluated implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Long, ResponseEvaluated> responsesEvaluated;

    private final List<Long> incorrectResponseIds;

    /**
     * TimeSpent is calculated approximately based on time between
     * a batch network round trip
     */
    private final long timeSpent;

    @JsonCreator
    public BatchEvaluated(@JsonProperty("responsesEvaluated") Map<Long, ResponseEvaluated> responsesEvaluated,
                          @JsonProperty("incorrectResponses") List<Long> incorrectResponseIds,
                          @JsonProperty("timeSpent") long timeSpent) {
        this.responsesEvaluated = responsesEvaluated;
        this.incorrectResponseIds = incorrectResponseIds;
        this.timeSpent = timeSpent;
    }
}
