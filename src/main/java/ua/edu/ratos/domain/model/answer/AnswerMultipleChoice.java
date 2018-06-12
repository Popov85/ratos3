package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.question.QuestionMultipleChoice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name="answer_mcq")
public class AnswerMultipleChoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private Long answerId;

    @Column(name="answer")
    private String answer;

    @Column(name="percent")
    private short percent;

    @Column(name="is_required")
    private boolean isRequired;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "answer_mcq_resource",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resources = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_mcq_question_question_id"))
    private QuestionMultipleChoice question;


    public boolean isValid() {
        if (answer == null) return false;
        if (answer.isEmpty())return false;
        if (percent < 0 || percent > 100) return false;
        if (percent==0 && isRequired) isRequired = false;
        return true;
    }
}
