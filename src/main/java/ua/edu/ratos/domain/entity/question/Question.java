package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Theme;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString (exclude = {"help", "resources", "theme", "language"})
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "question_id")
    protected Long questionId;

    @Column(name = "title")
    //@Size(min = 10, max = 200, message = "About Me must be between 10 and 200 characters")
    protected String question;

    @Column(name = "level")
    protected byte level;

    @Column(name = "is_deleted")
    protected boolean deleted;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    protected Theme theme;

    @ManyToOne
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    protected QuestionType type;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    protected Language lang;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Help help;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_resource", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
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
