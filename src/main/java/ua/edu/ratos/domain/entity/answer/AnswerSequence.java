package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.QuestionSequence;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name = "answer_sq")
@Where(clause = "is_deleted = 0")
@DynamicUpdate
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

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionSequence question;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "answer_sq_resource", joinColumns = @JoinColumn(name = "answer_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>();

    public void addResource(@NonNull Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(@NonNull Resource resource) {
        this.resources.remove(resource);
    }

    public boolean isValid() {
        if (this.phrase==null || this.phrase.isEmpty()) return false;
        if (order<0 || order>100) return false;
        return true;
    }
}
