package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import ua.edu.ratos.dao.entity.answer.AnswerFBSQ;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorValue(value = "2")
@DynamicUpdate
@NoArgsConstructor
public class QuestionFBSQ extends Question {

    @OneToOne(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    private AnswerFBSQ answer;

    public void addAnswer(AnswerFBSQ answer) {
        this.answer = answer;
        answer.setQuestion(this);
    }
}
