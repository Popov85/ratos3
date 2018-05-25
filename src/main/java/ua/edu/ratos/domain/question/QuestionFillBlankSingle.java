package ua.edu.ratos.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.ResponseFillBlankSingle;

import java.util.List;

@Setter
@Getter
@ToString
public class QuestionFillBlankSingle extends Question {

    private AnswerFillBlankSingle answer;

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answer == null || !this.answer.isValid()) return false;
        return true;
    }


    public int evaluate(ResponseFillBlankSingle response) {
        final String enteredPhrase = response.enteredPhrase;
        List<String> acceptedPhrases = answer.getAcceptedPhrases();
        // TODO check for isTypoAllowed and isCaseSensitive
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        return 0;
    }
}
