package ua.edu.ratos.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.session.Evaluator;

import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
public class ResponseFillBlankSingle implements Response {

    private final long questionId;
    private final String enteredPhrase;

    @Override
    public boolean isNullable() {
        return (enteredPhrase==null || enteredPhrase.isEmpty());
    }

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseFillBlankSingle that = (ResponseFillBlankSingle) o;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
