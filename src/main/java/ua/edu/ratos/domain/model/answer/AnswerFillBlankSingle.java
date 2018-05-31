package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

/**
 * Free-word answers, only one blank to fill in is acceptable, within this blank several words are acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString
//@Entity
@Table(name = "answer_fbsq")
public class AnswerFillBlankSingle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "set_id", foreignKey = @ForeignKey(name = "fk_answer_fbsq_settings_set_id"))
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AcceptedPhrase> acceptedPhrases;

    public boolean isValid() {
        if (this.settings == null) return false;
        if (this.settings.getLang() == null) return false;
        if (this.settings.getLang().isEmpty()) return false;
        if (this.settings.getWordsLimit() <1) return false;
        if (this.settings.getSymbolsLimit() <1) return false;
        if (this.acceptedPhrases==null) return false;
        if (this.acceptedPhrases.isEmpty()) return false;
        return true;
    }
}
