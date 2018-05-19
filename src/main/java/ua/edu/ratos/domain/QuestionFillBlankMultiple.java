package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.domain.answer.validator.AnswersFillBlankListValidator;
import ua.edu.ratos.domain.answer.validator.AnswersListValidator;
import ua.edu.ratos.service.dto.ResponseFillBlankMultiple;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@ToString
public class QuestionFillBlankMultiple extends Question implements Checkable<ResponseFillBlankMultiple> {

    private List<AnswerFillBlankMultiple> answers;

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answers == null) return false;
        if (this.answers.isEmpty()) return false;
        final AnswersListValidator validator = new AnswersFillBlankListValidator();
        if (!validator.isValid(this.answers)) return false;
        return true;
    }

    @Override
    public int check(@NonNull ResponseFillBlankMultiple response) {
        if (!this.isValid()) throw new RuntimeException("Invalid question");
        final List<ResponseFillBlankMultiple.Pair> pairs = response.getEnteredPhrases();
        for (ResponseFillBlankMultiple.Pair pair : pairs) {
            final Long phraseId = pair.getPhraseId();
            final String enteredPhrase = pair.getEnteredPhrase();
            Optional<AnswerFillBlankMultiple> answerFillBlankMultiple = findById(phraseId);
            if (!answerFillBlankMultiple.isPresent()) throw  new RuntimeException("Corrupt answerId");
            List<String> acceptedPhrases = answerFillBlankMultiple.get().getAcceptedPhrases();
            if (!acceptedPhrases.contains(enteredPhrase)) return 0;
        }
        return 100;
    }

    private Optional<AnswerFillBlankMultiple> findById(long answerId) {
        return answers.stream().filter(a -> a.getAnswerId() == answerId).findFirst();
    }


}
