package ua.edu.ratos.domain.answer.validator;

import lombok.NonNull;
import ua.edu.ratos.domain.answer.AnswerMultipleChoice;
import java.util.List;

public class AnswersMultipleChoiceListValidatorImpl implements AnswersMultipleChoiceListValidator {
    @Override
    public boolean isValid(@NonNull List<AnswerMultipleChoice> list) {
        int sum = 0;
        for (AnswerMultipleChoice a : list) {
            if (a == null) return false;
            if (!a.isValid()) return false;
            if (a.getPercent()==0 && a.isRequired()) a.setRequired(false);
            sum+=a.getPercent();
        }
        if (sum!=100) return false;
        return true;
    }
}