package ua.edu.ratos.domain.answer.validator;

import ua.edu.ratos.domain.answer.AnswerMultipleChoice;

import java.util.List;

public interface AnswersMultipleChoiceListValidator {
    boolean isValid(List<AnswerMultipleChoice> list);
}
