package ua.edu.ratos.domain.entity.answer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.question.QuestionFillBlankMultiple;
import ua.edu.ratos.service.dto.session.AnswerFBMQOutDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Free-word answerIds, multiple blanks to fill acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString(exclude = {"question", "settings", "acceptedPhrases"})
@Entity
@Table(name = "answer_fbmq")
@Cacheable
@Where(clause = "is_deleted = 0")
@DynamicUpdate
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

    @Column(name="is_deleted")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionFillBlankMultiple question;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private SettingsAnswerFillBlank settings;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "fbmq_phrase", joinColumns = {@JoinColumn(name = "answer_id") }, inverseJoinColumns = { @JoinColumn(name = "phrase_id")})
    private Set<Phrase> acceptedPhrases = new HashSet<>();

    public void addPhrase(@NonNull Phrase phrase) {
        this.acceptedPhrases.add(phrase);
    }

    public void removePhrase(@NonNull Phrase phrase) {
        this.acceptedPhrases.remove(phrase);
    }

    public AnswerFBMQOutDto toDto() {
        return new AnswerFBMQOutDto()
                .setAnswerId(this.answerId)
                .setPhrase(this.phrase)
                .setOccurrence(this.occurrence)
                .setWordsLimit(this.settings.getWordsLimit())
                .setSymbolsLimit(this.settings.getSymbolsLimit())
                .setLang(this.settings.getLang())
                .setCaseSensitive(this.settings.isCaseSensitive())
                .setNumeric(this.settings.isNumeric());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerFillBlankMultiple that = (AnswerFillBlankMultiple) o;
        return occurrence == that.occurrence &&
                Objects.equals(phrase, that.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase, occurrence);
    }
}
