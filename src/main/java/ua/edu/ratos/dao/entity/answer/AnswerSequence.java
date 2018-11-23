package ua.edu.ratos.dao.entity.answer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.question.QuestionSequence;
import ua.edu.ratos.service.session.domain.answer.AnswerSQ;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"question"})
@Entity
@Table(name = "answer_sq")
@Cacheable
@Where(clause = "is_deleted = 0")
@DynamicUpdate
public class AnswerSequence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "phrase_id")
    private Phrase phrase;

    @Column(name="phrase_order")
    private short order;

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionSequence question;

    public AnswerSQ toDomain() {
        return new AnswerSQ()
                .setAnswerId(this.answerId)
                .setPhrase(this.phrase.toDomain())
                .setOrder(this.order);
    }
}
