package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankSingle;
import ua.edu.ratos.service.dto.ResponseFillBlankSingle;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(callSuper = true, exclude = "answer")
@Entity
@DiscriminatorValue(value = "2")
public class QuestionFillBlankSingle extends Question {

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, optional = false)
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
