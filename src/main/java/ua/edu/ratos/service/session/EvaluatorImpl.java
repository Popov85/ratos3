package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import ua.edu.ratos.domain.entity.question.*;
import ua.edu.ratos.service.dto.response.*;

/**
 * @link https://stackoverflow.com/questions/30527947/polymorphism-and-dto-object-creation
 */
@AllArgsConstructor
public class EvaluatorImpl implements Evaluator {

    private final Question question;

    @Override
    public int evaluate(@NonNull final ResponseMultipleChoice response) {
        if (!(question instanceof QuestionMultipleChoice))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMultipleChoice.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMultipleChoice) question).evaluate(response);
    }

    @Override
    public int evaluate(@NonNull final ResponseFillBlankSingle response) {
        if (!(question instanceof QuestionFillBlankSingle))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFillBlankSingle.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFillBlankSingle) question).evaluate(response);
    }

    @Override
    public int evaluate(@NonNull final ResponseFillBlankMultiple response) {
        if (!(question instanceof QuestionFillBlankMultiple))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFillBlankMultiple.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFillBlankMultiple) question).evaluate(response);

    }

    @Override
    public int evaluate(@NonNull final ResponseMatcher response) {
        if (!(question instanceof QuestionMatcher))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMatcher.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMatcher) question).evaluate(response);
    }

    @Override
    public int evaluate(@NonNull final ResponseSequence response) {
        if (!(question instanceof QuestionSequence))
            throw new IllegalStateException("Type mismatch: expected :: QuestionSequence.class"+
                    " actual :: "+question.getClass());
        return ((QuestionSequence) question).evaluate(response);
    }

}
