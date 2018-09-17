package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.service.dto.response.ResponseMatcher;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Match.
 * Matching questions consist of two columns, typically one column is on the left and one column is on the right.
 * We will refer to the left side as 'Clues' and the right side as 'Matches'.
 * The objective is to pair the clues on the left side with their matches on the right.
 * These can be created with using help on both sides or a mix of help with media,
 * such as images, audio or video.
 *
 * @see <a href="https://www.classmarker.com/learn/question-types/matching-questions/">Match</a>
 * @author Andrey P.
 */

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "4")
@DynamicUpdate
public class QuestionMatcher extends Question {

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerMatcher> answers = new ArrayList<>();

    public void addAnswer(AnswerMatcher answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerMatcher answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

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

    @Override
    public String toString() {
        return "QuestionMatcher{" +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                ", type=" + type.getAbbreviation() +
                ", lang=" + lang.getAbbreviation() +
                '}';
    }
}
