package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import ua.edu.ratos.service.session.domain.question.*;
import ua.edu.ratos.service.session.domain.response.*;

/**
 * @link https://stackoverflow.com/questions/30527947/polymorphism-and-dto-object-creation
 */
@AllArgsConstructor
public class EvaluatorImpl implements Evaluator {

    private final Question question;

    @Override
    public double evaluate(@NonNull final ResponseMultipleChoice response) {
        if (!(question instanceof QuestionMCQ))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMCQ.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMCQ) question).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseFillBlankSingle response) {
        if (!(question instanceof QuestionFBSQ))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFBSQ.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFBSQ) question).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseFillBlankMultiple response) {
        if (!(question instanceof QuestionFBMQ))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFBMQ.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFBMQ) question).evaluate(response);

    }

    @Override
    public double evaluate(@NonNull final ResponseMatcher response) {
        if (!(question instanceof QuestionMQ))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMQ.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMQ) question).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseSequence response) {
        if (!(question instanceof QuestionSQ))
            throw new IllegalStateException("Type mismatch: expected :: QuestionSQ.class"+
                    " actual :: "+question.getClass());
        return ((QuestionSQ) question).evaluate(response);
    }

}
