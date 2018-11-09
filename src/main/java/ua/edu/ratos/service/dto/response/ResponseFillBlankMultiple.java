package ua.edu.ratos.service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.session.Evaluator;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
public class ResponseFillBlankMultiple implements Response {

    private final Long questionId;
    private final Set<Pair> enteredPhrases;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Pair {
        private final Long answerId;
        private final String enteredPhrase;
    }

    @Override
    public boolean isNullable() {
        return (enteredPhrases==null || enteredPhrases.isEmpty());
    }

    @Override
    public Double evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFillBlankMultiple that = (ResponseFillBlankMultiple) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
