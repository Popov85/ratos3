package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.ResponseFillBlankSingle;
import java.util.List;

@Setter
@Getter
@ToString
public class QuestionFillBlankSingle extends Question implements Checkable<ResponseFillBlankSingle> {

    private AnswerFillBlankSingle answer;

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answer == null || !this.answer.isValid()) return false;
        return true;
    }

    @Override
    public int check(@NonNull ResponseFillBlankSingle r) {
        if (!this.isValid()) throw new RuntimeException("Invalid question");
        final String enteredPhrase = r.getEnteredPhrase();
        List<String> acceptedPhrases = answer.getAcceptedPhrases();
        // TODO check for isTypoAllowed and isCaseSensitive
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        return 0;
    }
}
