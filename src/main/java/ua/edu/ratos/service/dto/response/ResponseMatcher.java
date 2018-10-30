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
public class ResponseMatcher implements Response {

    private final long questionId;
    private final Set<Triple> matchedPhrases;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Triple {
        public final long answerId;
        public final long leftPhraseId;
        public final long rightPhraseId;
    }

    @Override
    public boolean isNullable() {
        return (matchedPhrases==null || matchedPhrases.isEmpty());
    }

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMatcher that = (ResponseMatcher) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
