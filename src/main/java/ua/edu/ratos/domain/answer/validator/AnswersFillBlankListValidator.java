package ua.edu.ratos.domain.answer.validator;

import lombok.NonNull;
import ua.edu.ratos.domain.answer.AnswerFillBlankMultiple;

import java.util.List;

public class AnswersFillBlankListValidator implements AnswersListValidator<AnswerFillBlankMultiple> {

    /**
     *  Case 1: Simple (list = 1, valid, phrase - empty, occurrence - not matters (1))
     *  Case 2: Single (list = 1, valid, phrase - not empty, occurrence >0
     *  Case 3: Multi- (list = 2 or more, each valid, each phrase - not empty, each occurrence >0)
     * @param list
     * @return
     */
    @Override
    public boolean isValid(@NonNull List<AnswerFillBlankMultiple> list) {
        if (list.size()==1) {//Case 1 & 2
            AnswerFillBlankMultiple a = list.get(0);
            if (!a.isValid()) return false;
        } else { //Case 3
            for (AnswerFillBlankMultiple a : list) {
               if (!a.isValid() || a.getPhrase()==null || a.getPhrase().isEmpty() || a.getOccurrence()<1) return false;
            }
        }
        return true;
    }
}
