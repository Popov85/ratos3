package ua.edu.ratos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@ToString(exclude = {"staff", "phraseResource"})
@NoArgsConstructor
@Entity
@Table(name = "phrase")
@Cacheable
@DynamicUpdate
public class Phrase {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="phrase_id", nullable = false, updatable = false)
    private Long phraseId;

    @Column(name="phrase")
    private String phrase;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false, updatable = false)
    protected Staff staff;

    @JsonIgnore
    @Column(name="last_used", nullable = false)
    private LocalDateTime lastUsed = LocalDateTime.now();

    @Column(name="is_deleted")
    private boolean deleted;

    /**
     * Applicable only to Matcher and Sequence types
     */
    @OneToOne(mappedBy = "phrase", cascade = {CascadeType.ALL})
    private PhraseResource phraseResource;

    public Optional<PhraseResource> getPhraseResource() {
        return Optional.of(phraseResource);
    }

    public Phrase(String phrase) {
        this.phrase = phrase;
    }

    public ua.edu.ratos.service.session.domain.Phrase toDomain() {
        return new ua.edu.ratos.service.session.domain.Phrase()
                .setPhraseId(phraseId)
                .setPhrase(phrase)
                .setResource((getPhraseResource().isPresent()) ? getPhraseResource().get().getResource().toDomain(): null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase that = (Phrase) o;
        return Objects.equals(phrase, that.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase);
    }
}
