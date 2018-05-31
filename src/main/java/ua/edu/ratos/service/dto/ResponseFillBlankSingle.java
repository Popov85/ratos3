package ua.edu.ratos.service.dto;

import lombok.ToString;
import ua.edu.ratos.service.Evaluator;
import ua.edu.ratos.service.Response;

@ToString
public class ResponseFillBlankSingle implements Response {
    public long questionId;
    public String enteredPhrase;

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }
}
