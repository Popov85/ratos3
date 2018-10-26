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
public class ResponseMultipleChoice implements Response {

    private final long questionId;
    private final Set<Long> answerIds;

    @Override
    public int evaluateWith(Evaluator evaluator) {
        return evaluator.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMultipleChoice that = (ResponseMultipleChoice) o;
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
