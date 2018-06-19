package ua.edu.ratos.domain.entity.answer;

import lombok.Data;
import lombok.ToString;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankSingle;
import javax.persistence.*;
import java.util.List;

/**
 * Free-word answers, only one blank to fill in is acceptable, within this blank several words are acceptable
 * @author Andrey P.
 */
@Data
@ToString(exclude = {"question", "settings", "acceptedPhrases"})
@Entity
@Table(name = "answer_fbsq")
public class AnswerFillBlankSingle {

    @Id
    @Column(name="answer_id")
    private Long answerId;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id")
    private QuestionFillBlankSingle question;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "set_id", nullable=false)
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "fbsq_phrase", joinColumns = { @JoinColumn(name = "answer_id") }, inverseJoinColumns = { @JoinColumn(name = "phrase_id") })
    private List<AcceptedPhrase> acceptedPhrases;

    public boolean isValid() {
        if (this.settings == null) return false;
        if (this.settings.getLang() == null) return false;
        if (this.settings.getLang()==null) return false;
        if (this.settings.getWordsLimit() <1) return false;
        if (this.settings.getSymbolsLimit() <1) return false;
        if (this.acceptedPhrases==null) return false;
        if (this.acceptedPhrases.isEmpty()) return false;
        return true;
    }
}
