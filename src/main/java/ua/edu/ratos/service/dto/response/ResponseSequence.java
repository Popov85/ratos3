package ua.edu.ratos.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.session.Evaluator;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
public class ResponseSequence implements Response {

    private final Long questionId;
    private final List<Long> answerIds;

    @Override
    public Double evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseSequence that = (ResponseSequence) o;
        return questionId == that.questionId;
    }

    @Override
    public boolean isNullable() {
        return (answerIds==null || answerIds.isEmpty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
