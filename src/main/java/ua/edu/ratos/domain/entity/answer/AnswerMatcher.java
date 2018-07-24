package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name = "answer_mq")
@Where(clause = "is_deleted = 0")
@DynamicUpdate
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

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionMatcher question;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "answer_mq_resource", joinColumns = @JoinColumn(name = "answer_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>();

    public void addResource(@NonNull Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(@NonNull Resource resource) {
        this.resources.remove(resource);
    }


    public boolean isValid() {
        if (this.leftPhrase == null || this.rightPhrase==null) return false;
        if (this.leftPhrase.isEmpty()) return false;
        if (this.rightPhrase.isEmpty()) return false;
        return true;
    }
}
