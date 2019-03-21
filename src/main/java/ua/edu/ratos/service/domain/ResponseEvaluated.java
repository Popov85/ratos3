package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.service.domain.response.Response;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResponseEvaluated implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long questionId;
    private final Response response;
    /**
     *  Raw score, calculated by a corresponding question evaluator
     *  0 <= score <= 100
     */
    private final double score;

    @JsonCreator
    public ResponseEvaluated(@JsonProperty("questionId") Long questionId,
                             @JsonProperty("response") Response response,
                             @JsonProperty("score") double score) {
        this.questionId = questionId;
        this.response = response;
        this.score = score;
    }

    /**
     * Used in case when a user did not manage to provide any answer to this question
     * @param questionId
     * @return evaluated response
     */
    public static ResponseEvaluated buildEmpty(Long questionId) {
        return new ResponseEvaluated(questionId, null, 0);
    }
}