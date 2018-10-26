package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.domain.entity.*;
import ua.edu.ratos.service.dto.session.QuestionOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString (exclude = {"help", "resources", "theme", "language"})
@NoArgsConstructor
@Entity
@Table(name = "question")
@Cacheable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_id", discriminatorType = DiscriminatorType.INTEGER)
@Where(clause = "is_deleted = 0")
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "question_id", updatable = false, nullable = false)
    protected Long questionId;

    @Column(name = "title")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_help", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "help_id"))
    protected Set<Help> help = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_resource", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    protected Set<Resource> resources = new HashSet<>();

    protected transient CollectionShuffler collectionShuffler = new CollectionShuffler();

    public Question(String question, byte level) {
        this.question = question;
        this.level = level;
    }

    public void addHelp(Help help) {
        this.help.add(help);
    }

    public void removeHelp(Help help) {
        this.help.remove(help);
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

    /**
     * Converts this question to DTO for representing in a learning session;
     * @param mixable specifies the possibility to randomize answers
     * @return
     */
    public QuestionOutDto toDto(boolean mixable) {
        return new QuestionOutDto()
                .setQuestionId(this.questionId)
                .setQuestion(this.question)
                .setLevel(this.level)
                .setLang(this.lang)
                .setTheme(this.theme)
                .setType(this.type)
                //.setHelp(this.help)
                .setResources(this.resources);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(questionId, question1.questionId) &&
                Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, question);
    }
}
