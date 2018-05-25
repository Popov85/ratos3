package ua.edu.ratos.service.dto;


import lombok.ToString;
import ua.edu.ratos.service.Evaluator;
import ua.edu.ratos.service.Response;

import java.util.List;

@ToString
public class ResponseFillBlankMultiple implements Response {
    public List<Pair> enteredPhrases;

    public static class Pair {
        public Long phraseId;
        public String enteredPhrase;
    }

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }
}
