package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.dao.entity.answer.AnswerSequence;
import ua.edu.ratos.service.session.domain.question.QuestionSQ;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public QuestionSQ toDomain() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionSQ dto = modelMapper.map(this, QuestionSQ.class);
        dto.setLang(this.lang.getAbbreviation());
        dto.setType(this.type.getTypeId());
        dto.setHelp((super.helpToDomain().isPresent()) ? super.helpToDomain().get(): null);
        dto.setResources(super.resourcesToDomain());
        this.answers.forEach(a-> dto.add(a.toDomain()));
        return dto;
    }

}
