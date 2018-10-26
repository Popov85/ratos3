package ua.edu.ratos.service.dto.response;

import ua.edu.ratos.service.session.Evaluator;

public interface Response{
    long getQuestionId();
    boolean isNullable();
    int evaluateWith(Evaluator evaluator);
}
