package ua.edu.ratos.domain.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.service.dto.ResponseFillBlankMultiple;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
//@Entity
public class QuestionFillBlankMultiple extends Question{

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerFillBlankMultiple> answers;

    public int evaluate(ResponseFillBlankMultiple response) {
        final List<ResponseFillBlankMultiple.Pair> pairs = response.enteredPhrases;
        for (ResponseFillBlankMultiple.Pair pair : pairs) {
            final Long phraseId = pair.phraseId;
            final String enteredPhrase = pair.enteredPhrase;
            Optional<AnswerFillBlankMultiple> answerFillBlankMultiple =
                    answers.stream().filter(a -> a.getAnswerId() == phraseId).findFirst();
            if (!answerFillBlankMultiple.isPresent()) throw new RuntimeException("Corrupt answerId");
            List<String> acceptedPhrases = answerFillBlankMultiple.get().getAcceptedPhrases()
                    .stream()
                    .map(p->p.getPhrase())
                    .collect(Collectors.toList());
            if (!acceptedPhrases.contains(enteredPhrase)) return 0;
        }
        return 100;
    }
}
