package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.domain.entity.answer.AnswerSequence;
import ua.edu.ratos.service.dto.response.ResponseSequence;
import ua.edu.ratos.service.dto.session.QuestionOutDto;
import ua.edu.ratos.service.dto.session.QuestionSQOutDto;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Process.
 * This type of question matches the right sequence/steps/phases/stages to a control.
 * Objective is to check the provided response in the right sequence.
 * Wrong sequence of elements leads to the negative evaluation outcome.
 * @author Andrey P.
 */

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "5")
@DynamicUpdate
public class QuestionSequence extends Question {

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerSequence> answers = new ArrayList<>();

    public void addAnswer(AnswerSequence answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    /**
     * We only compare list of answers (to contain all the elements and to be in the right order of elements)
     * Strict match of sequences: correct and obtained sequence must be identical, {146, 222, 281, 300, 312} = {146, 222, 281, 300, 312}
     * If at least one mis-order or mis-match - result is fully incorrect  response (0)
     * @param response
     * @return
     */
    public int evaluate(ResponseSequence response) {
        final List<Long> responseSequence = response.getAnswerIds();
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
    public QuestionSQOutDto toDto(boolean mixable) {
        ModelMapper modelMapper = new ModelMapper();
        final QuestionOutDto questionOutDto = super.toDto(mixable);
        QuestionSQOutDto dto = modelMapper
                .map(questionOutDto, QuestionSQOutDto.class);
        this.answers.forEach(a-> dto.add(a.toDto()));
        if (mixable) {
            dto.setAnswers(collectionShuffler
                .shuffle(dto.getAnswers())
                .stream()
                .collect(Collectors.toSet()));
        }
        return dto;
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
