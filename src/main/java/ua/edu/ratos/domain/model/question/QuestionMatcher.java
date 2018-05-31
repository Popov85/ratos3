package ua.edu.ratos.domain.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.answer.AnswerMatcher;
import ua.edu.ratos.service.dto.ResponseMatcher;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * Match.
 * Matching questions consist of two columns, typically one column is on the left and one column is on the right.
 * We will refer to the left side as 'Clues' and the right side as 'Matches'.
 * The objective is to pair the clues on the left side with their matches on the right.
 * These can be created with using text on both sides or a mix of text with media,
 * such as images, audio or video.
 *
 * @see <a href="https://www.classmarker.com/learn/question-types/matching-questions/">Match</a>
 * @author Andrey P.
 */

@Setter
@Getter
@ToString
//@Entity
public class QuestionMatcher extends Question {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerMatcher> answers;

    public int evaluate(ResponseMatcher response) {
        final List<ResponseMatcher.Triple> responses = response.responses;
        for (ResponseMatcher.Triple r : responses) {
            final long answerId = r.answerId;
            final String obtainedLeftPhrase = r.leftPhrase;
            final String obtainedRightPhrase = r.rightPhrase;
            final Optional<AnswerMatcher> answerMatcher =
                    answers.stream().filter(a -> a.getAnswerId() == answerId).findFirst();
            final String correctLeftPhrase = answerMatcher.get().getLeftPhrase();
            final String correctRightPhrase = answerMatcher.get().getRightPhrase();
            if (!obtainedLeftPhrase.equals(correctLeftPhrase) || !obtainedRightPhrase.equals(correctRightPhrase)) return 0;
        }
        return 100;
    }
}
