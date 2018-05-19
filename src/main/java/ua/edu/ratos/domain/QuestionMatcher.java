package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerMatcher;
import ua.edu.ratos.service.dto.ResponseMatcher;
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
public class QuestionMatcher extends Question implements Checkable<ResponseMatcher> {

    private List<AnswerMatcher> answers;

    @Override
    public int check(ResponseMatcher responseMatcher) {
        final List<ResponseMatcher.Triple> responses = responseMatcher.getResponses();
        for (ResponseMatcher.Triple response : responses) {
            final long answerId = response.getAnswerId();
            final long obtainedLeftPhraseId = response.getLeftPhraseId();
            final long obtainedRightPhraseId = response.getRightPhraseId();
            final Optional<AnswerMatcher> answerMatcher = findById(answerId);
            final long correctLeftPhraseId = answerMatcher.get().getLeftMatcher().getLeftPhraseId();
            final long correctRightPhraseId = answerMatcher.get().getRightMatcher().getRightPhraseId();
            if (obtainedLeftPhraseId!=correctLeftPhraseId || obtainedRightPhraseId!=correctRightPhraseId) return 0;
        }
        return 100;
    }

    private Optional<AnswerMatcher> findById(long answerId) {
        return answers.stream().filter(a -> a.getAnswerId() == answerId).findFirst();
    }
}
