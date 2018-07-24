package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Theme;
import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString (exclude = {"help", "resources", "theme", "language"})
@NoArgsConstructor
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "question_id", updatable = false, nullable = false)
    protected Long questionId;

    @Column(name = "title")
    //@Size(min = 10, max = 200, message = "About Me must be between 10 and 200 characters")
    protected String question;

    @Column(name = "level")
    protected byte level;

    @Column(name = "is_deleted")
    protected boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", updatable = false)
    protected Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    protected QuestionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id")
    protected Language lang;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Help> help = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_resource", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    protected Set<Resource> resources = new HashSet<>();

    public Question(String question, byte level) {
        this.question = question;
        this.level = level;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(Resource resource) {
        this.resources.remove(resource);
    }

    public Optional<Set<Help>> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<Set<Resource>> getResources() {
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
