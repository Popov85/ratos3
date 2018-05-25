package ua.edu.ratos.service.dto;

import lombok.ToString;
import ua.edu.ratos.service.Evaluator;
import ua.edu.ratos.service.Response;
import java.util.List;

@ToString
public class ResponseMultipleChoice implements Response {

    public long questionId;
    public List<Long> ids;

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

}
