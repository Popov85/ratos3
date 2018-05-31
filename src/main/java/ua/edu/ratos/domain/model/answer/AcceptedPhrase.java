package ua.edu.ratos.domain.model.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Setter
@Getter
@ToString
//@Entity
@Table(name = "accepted_phrase")
public class AcceptedPhrase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long phraseId;

    @Column(name="phrase")
    private String phrase;
}
