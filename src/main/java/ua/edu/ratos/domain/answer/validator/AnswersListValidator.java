package ua.edu.ratos.domain.answer.validator;

import ua.edu.ratos.domain.answer.Answer;
import java.util.List;

public interface AnswersListValidator<T extends Answer> {
    boolean isValid(List<T> list);
}
