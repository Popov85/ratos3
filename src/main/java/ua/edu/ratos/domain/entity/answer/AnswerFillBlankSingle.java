package ua.edu.ratos.domain.entity.answer;

import lombok.*;
import ua.edu.ratos.domain.entity.AcceptedPhrase;
import ua.edu.ratos.domain.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankSingle;
import ua.edu.ratos.service.dto.session.AnswerFBSQOutDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Free-word answers, only one blank to fill in is acceptable, within this blank several words are acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString(exclude = {"question", "settings", "acceptedPhrases"})
@NoArgsConstructor
@Entity
@Table(name = "answer_fbsq")
@Cacheable
public class AnswerFillBlankSingle {

    @Id
    @Column(name="answer_id")
    private Long answerId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false, updatable = false)
    private QuestionFillBlankSingle question;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable=false)
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "fbsq_phrase", joinColumns = { @JoinColumn(name = "answer_id") }, inverseJoinColumns = { @JoinColumn(name = "phrase_id") })
    private Set<AcceptedPhrase> acceptedPhrases = new HashSet<>();

    public void addPhrase(@NonNull AcceptedPhrase phrase) {
        this.acceptedPhrases.add(phrase);
    }

    public void removePhrase(@NonNull AcceptedPhrase phrase) {
        this.acceptedPhrases.remove(phrase);
    }

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

    public AnswerFBSQOutDto toDto() {
        return new AnswerFBSQOutDto()
                .setAnswerId(this.answerId)
                .setWordsLimit(this.settings.getWordsLimit())
                .setSymbolsLimit(this.settings.getSymbolsLimit())
                .setLang(this.settings.getLang())
                .setCaseSensitive(this.settings.isCaseSensitive())
                .setNumeric(this.settings.isNumeric());
    }
}
