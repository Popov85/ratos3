package ua.edu.ratos.domain.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.Help;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.Theme;
import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@ToString
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    protected Long questionId;

    @Column(name="title")
    protected String question;

    @Column(name="level")
    protected byte level;

    @ManyToOne
    @JoinColumn(name = "theme_id", foreignKey = @ForeignKey(name = "fk_question_theme_theme_id"))
    protected Theme theme;

    @OneToOne
    @JoinColumn(name = "question_id")
    protected Help help;

    @ManyToOne
    @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_question_resource_resource_id"))
    protected Resource resource;

    public Optional<Help> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    public boolean isValid() {
        if (this.question==null) return false;
        if (this.question.isEmpty()) return false;
        if (this.level <1 || this.level>3) return false;
        //if (this.theme==null) return false;
        return true;
    }
}
