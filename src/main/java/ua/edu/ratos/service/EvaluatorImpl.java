package ua.edu.ratos.service;

import lombok.NonNull;
import ua.edu.ratos.domain.question.*;
import ua.edu.ratos.service.dto.*;

public class EvaluatorImpl implements Evaluator {

    private final Question question;

    public EvaluatorImpl(@NonNull Question question) {
        this.question = question;
    }

    @Override
    public int evaluate(ResponseMultipleChoice response) {
        if (!(question instanceof QuestionMultipleChoice))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMultipleChoice.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMultipleChoice) question).evaluate(response);
    }

    @Override
    public int evaluate(ResponseFillBlankSingle response) {
        if (!(question instanceof QuestionFillBlankSingle))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFillBlankSingle.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFillBlankSingle) this.question).evaluate(response);
    }

    @Override
    public int evaluate(ResponseFillBlankMultiple response) {
        if (!(question instanceof QuestionFillBlankMultiple))
            throw new IllegalStateException("Type mismatch: expected :: QuestionFillBlankMultiple.class"+
                    " actual :: "+question.getClass());
        return ((QuestionFillBlankMultiple) this.question).evaluate(response);

    }

    @Override
    public int evaluate(ResponseMatcher response) {
        if (!(question instanceof QuestionMatcher))
            throw new IllegalStateException("Type mismatch: expected :: QuestionMatcher.class"+
                    " actual :: "+question.getClass());
        return ((QuestionMatcher) this.question).evaluate(response);
    }

    @Override
    public int evaluate(ResponseSequence response) {
        if (!(question instanceof QuestionSequence))
            throw new IllegalStateException("Type mismatch: expected :: QuestionSequence.class"+
                    " actual :: "+question.getClass());
        return ((QuestionSequence) this.question).evaluate(response);
    }

}
