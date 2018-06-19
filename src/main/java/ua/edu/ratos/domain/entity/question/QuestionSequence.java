package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.service.dto.ResponseSequence;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Process.
 * This type of question matches the right sequence/steps/phases/stages of a process.
 * Objective is to currentRow the provided steps in the right sequence.
 * Wrong sequence of e leads to wrong answerIds.
 * @author Andrey P.
 */

@Setter
@Getter
@ToString(callSuper = true, exclude = "answers")
@Entity
@DiscriminatorValue(value = "5")
public class QuestionSequence extends Question {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerSequence> answers;

    public int evaluate(ResponseSequence response) {
        final List<Long> responseSequence = response.answerIds;
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
