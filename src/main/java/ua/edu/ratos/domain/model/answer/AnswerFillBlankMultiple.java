package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

/**
 * Free-word answerIds, multiple blanks to fill acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString
//@Entity
@Table(name = "answer_fbmq")
public class AnswerFillBlankMultiple{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long answerId;

    @Column(name="phrase")
    private String phrase;

    @Column(name="occurrence")
    private byte occurrence = 1;

    @ManyToOne
    @JoinColumn(name = "set_id", foreignKey = @ForeignKey(name = "fk_answer_fbmq_settings_set_id"))
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AcceptedPhrase> acceptedPhrases;

    public boolean isValid() {
        if (this.phrase == null || this.phrase.isEmpty()) return false;
        if (this.occurrence <0 || this.occurrence>100) return false;
        return true;
    }
}
