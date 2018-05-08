package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Answer;
/**
 * Free-word question, one-two word input accepted.
 * @author Ansery P.
 */
@Setter
@Getter
@ToString
public class AnswerTypeC implements Answer {
    private long answerId;
    private String acceptedPhrase;
}
