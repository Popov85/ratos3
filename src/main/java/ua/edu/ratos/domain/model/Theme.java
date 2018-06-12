package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.question.Question;
import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "questions")
@Entity
@Table(name="theme")
public class Theme {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long themeId;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

}
