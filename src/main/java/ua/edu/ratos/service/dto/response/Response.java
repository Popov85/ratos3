package ua.edu.ratos.service.dto.response;

import ua.edu.ratos.service.session.Evaluator;

public interface Response{
    Long getQuestionId();
    boolean isNullable();
    Double evaluateWith(Evaluator evaluator);
}
