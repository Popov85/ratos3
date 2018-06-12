package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.question.QuestionSequence;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name = "answer_sq")
public class AnswerSequence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private long answerId;

    @Column(name="element")
    private String phrase;

    @Column(name="element_order")
    private short order;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_sq_question_question_id"))
    private QuestionSequence question;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "answer_sq_resource",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resources;

    public boolean isValid() {
        if (this.phrase==null || this.phrase.isEmpty()) return false;
        if (order<0 || order>100) return false;
        return true;
    }
}
