package ua.edu.ratos.service.domain.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ua.edu.ratos.service.session.Evaluator;

/**
 * @see ResponseMCQ
 * @see ResponseFBSQ
 * @see ResponseFBMQ
 * @see ResponseMQ
 * @see ResponseSQ
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public interface Response{
    Long getQuestionId();
    boolean isNullable();
    Double evaluateWith(Evaluator evaluator);
}
