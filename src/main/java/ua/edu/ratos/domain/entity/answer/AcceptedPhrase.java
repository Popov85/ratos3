package ua.edu.ratos.domain.entity.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name = "accepted_phrase")
public class AcceptedPhrase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="phrase_id")
    private Long phraseId;

    @Column(name="phrase")
    private String phrase;
}
