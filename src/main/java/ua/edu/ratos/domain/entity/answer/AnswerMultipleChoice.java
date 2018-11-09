package ua.edu.ratos.domain.entity.answer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.QuestionMultipleChoice;
import ua.edu.ratos.service.dto.session.AnswerMCQOutDto;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@NoArgsConstructor
@Entity
@Table(name="answer_mcq")
@Cacheable
@Where(clause = "is_deleted = 0")
@DynamicUpdate
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

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionMultipleChoice question;

    /**
     * One-to-one actually, but for the sake of simplicity (so that not to create a separate class like PhraseResource),
     * we use Many-to-many
     */
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "answer_mcq_resource", joinColumns = @JoinColumn(name = "answer_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>();

    public void addResource(Resource resource) {
        if (!this.resources.isEmpty())
            throw new IllegalStateException("Currently, only one resource can be associated with an answer");
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }

    public AnswerMultipleChoice(String answer, short percent, boolean isRequired) {
        this.answer = answer;
        this.percent = percent;
        this.isRequired = isRequired;
    }

    public boolean isValid() {
        if (answer == null) return false;
        if (answer.isEmpty())return false;
        if (percent < 0 || percent > 100) return false;
        if (percent==0 && isRequired) isRequired = false;
        return true;
    }

    public AnswerMCQOutDto toDto() {
        return new AnswerMCQOutDto()
                .setAnswerId(this.answerId)
                .setAnswer(this.answer)
                .setResource(this.resources.isEmpty() ? null : this.resources.iterator().next());
    }

}
