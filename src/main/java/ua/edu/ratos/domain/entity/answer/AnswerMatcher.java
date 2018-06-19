package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"question", "resource"})
@Entity
@Table(name = "answer_mq")
public class AnswerMatcher {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private long answerId;

    @Column(name="left_phrase")
    private String leftPhrase;

    @Column(name="right_phrase")
    private String rightPhrase;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "question_id")
    private QuestionMatcher question;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "answer_mq_resource",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resources;

    public boolean isValid() {
        if (this.leftPhrase == null || this.rightPhrase==null) return false;
        if (this.leftPhrase.isEmpty()) return false;
        if (this.rightPhrase.isEmpty()) return false;
        return true;
    }
}
