package ua.edu.ratos.domain.entity.answer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;
import ua.edu.ratos.service.dto.session.AnswerMQOutDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {"question", "resources"})
@Entity
@Table(name = "answer_mq")
@Cacheable
@Where(clause = "is_deleted = 0")
@DynamicUpdate
public class AnswerMatcher {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private Long answerId;

    @Column(name="left_phrase")
    private String leftPhrase;

    @Column(name="right_phrase")
    private String rightPhrase;

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionMatcher question;

    /**
     * Only right phrase can be associated with a single resource
     */
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "answer_mq_resource", joinColumns = @JoinColumn(name = "answer_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>(1);

    public void addResource(@NonNull Resource resource) {
        if (!this.resources.isEmpty()) throw new IllegalStateException("Right phrase can only be associated with exactly one resource (image, audio, video)");
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

    public AnswerMQOutDto toDto() {
        return new AnswerMQOutDto()
                .setAnswerId(this.answerId)
                .setLeftPhrase(this.leftPhrase)
                .setResource(this.resources.isEmpty() ? null : this.resources.iterator().next());
    }
}
