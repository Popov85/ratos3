package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Staff;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString(exclude = "staff")
@NoArgsConstructor
@Entity
@Table(name = "accepted_phrase")
@DynamicUpdate
public class AcceptedPhrase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="phrase_id", nullable = false, updatable = false)
    private Long phraseId;

    @Column(name="phrase")
    private String phrase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false, updatable = false)
    protected Staff staff;

    @Column(name="last_used", nullable = false)
    private LocalDateTime lastUsed = LocalDateTime.now();

    public AcceptedPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcceptedPhrase that = (AcceptedPhrase) o;
        return Objects.equals(phrase, that.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase);
    }
}
