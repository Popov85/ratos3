package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Answer;
import ua.edu.ratos.domain.Resource;
import java.util.Optional;

/**
 * Multiple-choice question, one possible answer.
 * @author Ansery P.
 */
@Setter
@Getter
@ToString
public class AnswerTypeA implements Answer {
    private long answerId;
    private String answer;
    private boolean isCorrect;
    private Optional<Resource> resource;
}
