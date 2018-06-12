package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.question.QuestionFillBlankMultiple;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Free-word answerIds, multiple blanks to fill acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString(exclude = {"question", "settings", "acceptedPhrases"})
@Entity
@Table(name = "answer_fbmq")
public class AnswerFillBlankMultiple{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="answer_id")
    private Long answerId;

    @Column(name="phrase")
    private String phrase;

    @Column(name="occurrence")
    private byte occurrence = 1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_fbmq_question_question_id"))
    private QuestionFillBlankMultiple question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id", foreignKey = @ForeignKey(name = "fk_answer_fbmq_settings_set_id"))
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "fbmq_phrase", joinColumns = { @JoinColumn(name = "answer_id") }, inverseJoinColumns = { @JoinColumn(name = "phrase_id") })
    private List<AcceptedPhrase> acceptedPhrases = new ArrayList<>();

    public boolean isValid() {
        if (this.phrase == null || this.phrase.isEmpty()) return false;
        if (this.occurrence <0 || this.occurrence>100) return false;
        return true;
    }
}
