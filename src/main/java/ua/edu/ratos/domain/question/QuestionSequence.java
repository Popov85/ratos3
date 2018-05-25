package ua.edu.ratos.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.answer.AnswerSequence;
import ua.edu.ratos.service.dto.ResponseSequence;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Process.
 * This type of question matches the right sequence/steps/phases/stages of a process.
 * Objective is to currentRow the provided steps in the right sequence.
 * Wrong sequence of e leads to wrong answer.
 * @author Andrey P.
 */

@Setter
@Getter
@ToString
public class QuestionSequence extends Question {
    private List<AnswerSequence> answers;

    public int evaluate(ResponseSequence response) {
        final List<Long> responseSequence = response.answer;
        if (responseSequence.equals(findAll())) return 100;
        return 0;
    }

    private List<Long> findAll() {
        return answers.stream()
                .sorted(Comparator.comparingInt(AnswerSequence::getOrder))
                .map(AnswerSequence::getAnswerId)
                .collect(Collectors.toList());
    }
}
