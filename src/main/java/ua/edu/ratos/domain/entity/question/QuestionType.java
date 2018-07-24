package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Table(name="question_type")
public class QuestionType {
    @Id
    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "eng_abbreviation")
    private String abbreviation;

    @Column(name = "description")
    private String description;
}
