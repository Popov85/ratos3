package ua.edu.ratos.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.service.dto.ResponseFillBlankMultiple;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@ToString
public class QuestionFillBlankMultiple extends Question{
    private List<AnswerFillBlankMultiple> answers;

    public int evaluate(ResponseFillBlankMultiple response) {
        final List<ResponseFillBlankMultiple.Pair> pairs = response.enteredPhrases;
        for (ResponseFillBlankMultiple.Pair pair : pairs) {
            final Long phraseId = pair.phraseId;
            final String enteredPhrase = pair.enteredPhrase;
            Optional<AnswerFillBlankMultiple> answerFillBlankMultiple =
                    answers.stream().filter(a -> a.getAnswerId() == phraseId).findFirst();
            if (!answerFillBlankMultiple.isPresent()) throw new RuntimeException("Corrupt answerId");
            List<String> acceptedPhrases = answerFillBlankMultiple.get().getAcceptedPhrases();
            if (!acceptedPhrases.contains(enteredPhrase)) return 0;
        }
        return 100;
    }
}
