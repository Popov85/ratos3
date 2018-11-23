package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.dao.entity.answer.AnswerMultipleChoice;
import ua.edu.ratos.service.session.domain.question.QuestionMCQ;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Cacheable
@DiscriminatorValue(value = "1")
@DynamicUpdate
public class QuestionMultipleChoice extends Question {

    @Transient
    private boolean isSingle;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerMultipleChoice> answers = new ArrayList<>();

    public void addAnswer(AnswerMultipleChoice answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerMultipleChoice answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

    /**
     * Creates a new empty Question object (for parsers)
     * @return newly created empty Question
     */
    public static QuestionMultipleChoice createEmpty() {
        QuestionMultipleChoice q = new QuestionMultipleChoice();
        q.setQuestion("");
        q.setLevel((byte)1);
        q.setAnswers(new ArrayList<>());
        return q;
    }
    /**
     * Checks whether this question contains only one correct answerIds or multiple correct answers
     * @return
     */
    public boolean isSingle() {
        if (!isValid()) throw new RuntimeException("Invalid question");
        int counter = 0;
        for (AnswerMultipleChoice answer : answers) {
            if (!answer.isValid()) throw new RuntimeException("Invalid answerIds");
            if (answer.getPercent()!=0) counter++;
        }
       return (counter > 1) ? false : true;
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.answers == null) return false;
        if (this.answers.isEmpty()) return false;
        if (this.answers.size()<2) return false;
        if (!isValid(this.answers)) return false;
        return true;
    }

    private boolean isValid(@NonNull List<AnswerMultipleChoice> list) {
        int sum = 0;
        for (AnswerMultipleChoice a : list) {
            if (a == null) return false;
            if (!a.isValid()) return false;
            if (a.getPercent()==0 && a.isRequired()) a.setRequired(false);
            sum+=a.getPercent();
        }
        if (sum!=100) return false;
        return true;
    }

    @Override
    public QuestionMCQ toDomain() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMCQ questionMCQ = modelMapper
                .map(this, QuestionMCQ.class)
                .setSingle(isSingle());
        questionMCQ.setLang(this.lang.getAbbreviation());
        questionMCQ.setType(this.type.getTypeId());
        questionMCQ.setHelp((super.helpToDomain().isPresent()) ? super.helpToDomain().get(): null);
        questionMCQ.setResources(super.resourcesToDomain());
        this.answers.forEach(a-> questionMCQ.addAnswer(a.toDomain()));
        return questionMCQ;
    }
}
