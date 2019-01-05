package ua.edu.ratos.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

@Setter
@Getter
@ToString
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
