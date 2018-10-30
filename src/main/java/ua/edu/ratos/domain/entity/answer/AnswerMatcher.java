package ua.edu.ratos.domain.entity.answer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.question.QuestionMatcher;
import ua.edu.ratos.service.dto.session.AnswerMQOutDto;
import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"question"})
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

    @ManyToOne
    @JoinColumn(name = "left_phrase_id")
    private Phrase leftPhrase;

    @ManyToOne
    @JoinColumn(name = "right_phrase_id")
    private Phrase rightPhrase;

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionMatcher question;


    public AnswerMQOutDto toDto() {
        return new AnswerMQOutDto()
                .setAnswerId(this.answerId)
                .setLeftPhrase(this.leftPhrase);
    }
}
