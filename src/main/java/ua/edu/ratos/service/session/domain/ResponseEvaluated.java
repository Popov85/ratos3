package ua.edu.ratos.service.session.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.entity.question.Question;
import ua.edu.ratos.service.dto.response.Response;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseEvaluated {
    private final Question question;
    private final Response response;
    /**
     *  Raw score, calculated by a corresponding question evaluator
     *  0 <= score <= 100
     */
    private final double score;

    /**
     * Used in case when a user did not manage to provide any answer to this question
     * @param question
     * @return evaluated response
     */
    public static ResponseEvaluated buildEmpty(Question question) {
        return new ResponseEvaluated(question, null, 0);
    }
}