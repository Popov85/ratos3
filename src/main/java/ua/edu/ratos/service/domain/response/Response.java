package ua.edu.ratos.service.domain.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ua.edu.ratos.service.session.Evaluator;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public interface Response{
    Long getQuestionId();
    boolean isNullable();
    Double evaluateWith(Evaluator evaluator);
}
