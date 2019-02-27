package ua.edu.ratos.dao.entity.answer;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ua.edu.ratos.dao.entity.Phrase;
import ua.edu.ratos.dao.entity.SettingsFB;
import ua.edu.ratos.dao.entity.question.QuestionFBSQ;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Free-word answers, only one blank to fill in is acceptable, within this blank several words are acceptable.
 * @author Andrey P.
 */
@Setter
@Getter
@ToString(exclude = {"question", "settings", "acceptedPhrases"})
@NoArgsConstructor
@Entity
@Table(name = "answer_fbsq")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnswerFBSQ {

    @Id
    @Column(name="answer_id")
    private Long answerId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false, updatable = false)
    private QuestionFBSQ question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", nullable=false)
    private SettingsFB settings;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "fbsq_phrase", joinColumns = { @JoinColumn(name = "answer_id") }, inverseJoinColumns = { @JoinColumn(name = "phrase_id") })
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Phrase> acceptedPhrases = new HashSet<>();

    public void addPhrase(@NonNull Phrase phrase) {
        this.acceptedPhrases.add(phrase);
    }
}
