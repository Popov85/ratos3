package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Resource;
import java.util.Optional;

/**
 * Process.
 * This type of question matches the right sequence/steps/phases/stages of a process.
 * Objective is to currentRow the provided steps in the right sequence.
 * Wrong sequence leads to wrong answer.
 * @author Andrey P.
 */
@Setter
@Getter
@ToString
public class AnswerTypeE {
    private long answerId;
    private String phrase;
    private short order;
    private Optional<Resource> resource;
}
