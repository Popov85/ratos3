package ua.edu.ratos.domain.model.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.model.Help;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.Theme;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString (exclude = {"help", "resources", "theme"})
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "question_id")
    protected Long questionId;

    @Column(name = "title")
    protected String question;

    @Column(name = "level")
    protected byte level;

    @ManyToOne
    @JoinColumn(name = "theme_id", foreignKey = @ForeignKey(name = "fk_question_theme_theme_id"))
    protected Theme theme;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    protected Help help;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_resource",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    protected List<Resource> resources = new ArrayList<>();

    public Optional<Help> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<List<Resource>> getResource() {
        return Optional.ofNullable(resources);
    }

    public boolean isValid() {
        if (this.question == null) return false;
        if (this.question.isEmpty()) return false;
        if (this.level < 1 || this.level > 3) return false;
        //if (this.theme==null) return false;
        return true;
    }

}
