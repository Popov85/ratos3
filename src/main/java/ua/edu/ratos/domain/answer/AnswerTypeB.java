package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Answer;
import ua.edu.ratos.domain.Resource;
import java.util.Optional;
/**
 * Multiple-choice question, several possible answers.
 * @author Ansery P.
 */
@Setter
@Getter
@ToString
public class AnswerTypeB implements Answer {
    private long answerId;
    private String answer;
    private short percent;
    private boolean isRequired;
    private Optional<Resource> resource;
}
