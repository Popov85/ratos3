package ua.edu.ratos.domain.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.ResponseFillBlankSingle;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
//@Entity
public class QuestionFillBlankSingle extends Question {

    @OneToOne
    @JoinColumn(name = "question_id")
    private AnswerFillBlankSingle answer;

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answer == null || !this.answer.isValid()) return false;
        return true;
    }

    public int evaluate(ResponseFillBlankSingle response) {
        final String enteredPhrase = response.enteredPhrase;
        List<String> acceptedPhrases = answer.getAcceptedPhrases()
                .stream()
                .map(p->p.getPhrase())
                .collect(Collectors.toList());
        // TODO check for isTypoAllowed and isCaseSensitive
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        return 0;
    }
}
