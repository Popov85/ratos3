package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import ua.edu.ratos.service.domain.question.*;
import ua.edu.ratos.service.domain.response.*;

/**
 * @link https://stackoverflow.com/totalByType/30527947/polymorphism-and-dto-object-creation
 */
@AllArgsConstructor
public class EvaluatorImpl implements Evaluator {

    private final QuestionDomain questionDomain;

    @Override
    public double evaluate(@NonNull final ResponseMCQ response) {
        if (!(questionDomain instanceof QuestionMCQDomain))
            throw new IllegalStateException("Type mismatch: expected = QuestionMCQDomain.class"+
                    " actual = "+ questionDomain.getClass());
        return ((QuestionMCQDomain) questionDomain).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseFBSQ response) {
        if (!(questionDomain instanceof QuestionFBSQDomain))
            throw new IllegalStateException("Type mismatch: expected = QuestionFBSQDomain.class"+
                    " actual = "+ questionDomain.getClass());
        return ((QuestionFBSQDomain) questionDomain).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseFBMQ response) {
        if (!(questionDomain instanceof QuestionFBMQDomain))
            throw new IllegalStateException("Type mismatch: expected = QuestionFBMQDomain.class"+
                    " actual = "+ questionDomain.getClass());
        return ((QuestionFBMQDomain) questionDomain).evaluate(response);

    }

    @Override
    public double evaluate(@NonNull final ResponseMQ response) {
        if (!(questionDomain instanceof QuestionMQDomain))
            throw new IllegalStateException("Type mismatch: expected = QuestionMQDomain.class"+
                    " actual = "+ questionDomain.getClass());
        return ((QuestionMQDomain) questionDomain).evaluate(response);
    }

    @Override
    public double evaluate(@NonNull final ResponseSQ response) {
        if (!(questionDomain instanceof QuestionSQDomain))
            throw new IllegalStateException("Type mismatch: expected = QuestionSQDomain.class"+
                    " actual = "+ questionDomain.getClass());
        return ((QuestionSQDomain) questionDomain).evaluate(response);
    }

}
