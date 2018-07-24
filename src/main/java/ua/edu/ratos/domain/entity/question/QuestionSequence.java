package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.service.dto.response.ResponseSequence;

import javax.persistence.*;
import java.util.ArrayList;
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
@Entity
@DiscriminatorValue(value = "5")
@NamedEntityGraph(name = "SQ.enriched", attributeNodes = {
        @NamedAttributeNode("answers"),
        @NamedAttributeNode("resources"),
        @NamedAttributeNode("help"),
        @NamedAttributeNode("theme")
})
@DynamicUpdate
public class QuestionSequence extends Question {

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<AnswerSequence> answers = new ArrayList<>();

    public void addAnswer(AnswerSequence answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerSequence answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

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

    @Override
    public String toString() {
        return "QuestionSequence{" +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                ", type=" + type.getAbbreviation() +
                ", lang=" + lang.getAbbreviation() +
                '}';
    }
}
