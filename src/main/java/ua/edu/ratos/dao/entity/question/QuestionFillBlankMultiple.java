package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.dao.entity.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.service.session.domain.question.QuestionFBMQ;
import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "3")
@DynamicUpdate
public class QuestionFillBlankMultiple extends Question{

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnswerFillBlankMultiple> answers = new HashSet<>();

    public void addAnswer(AnswerFillBlankMultiple answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerFillBlankMultiple answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

    @Override
    public QuestionFBMQ toDomain() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBMQ questionFBMQ = modelMapper.map(this, QuestionFBMQ.class);
        questionFBMQ.setLang(this.lang.getAbbreviation());
        questionFBMQ.setType(this.type.getTypeId());
        questionFBMQ.setHelp((super.helpToDomain().isPresent()) ? super.helpToDomain().get(): null);
        questionFBMQ.setResources(super.resourcesToDomain());
        this.answers.forEach(a-> questionFBMQ.addAnswer(a.toDomain()));
        return questionFBMQ;
    }
}
