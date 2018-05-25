package ua.edu.ratos.service.dto;

import lombok.ToString;
import ua.edu.ratos.service.Evaluator;
import ua.edu.ratos.service.Response;

import java.util.List;

@ToString
public class ResponseMatcher implements Response {
    public long questionId;
    public List<Triple> responses;

    public static class Triple {
        public long answerId;
        public long leftPhraseId;
        public long rightPhraseId;
    }

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }
}
