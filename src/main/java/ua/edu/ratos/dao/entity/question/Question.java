package ua.edu.ratos.dao.entity.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.session.domain.Resource;
import javax.persistence.*;
import java.util.*;

@Getter
@Setter
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

    // Technically many Helps can be associated with a question, but we go for only one for now
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_help", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "help_id"))
    protected Set<ua.edu.ratos.dao.entity.Help> help = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "question_resource", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    protected Set<ua.edu.ratos.dao.entity.Resource> resources = new HashSet<>();

    /**
     * Only applicable to FBMQ & MQ (in MCQ's partiality is managed by isRequired flag);
     * Specifies whether partial correct response is allowed or not;
     * For example, you matched correctly 3 out of 5 phrases in MQ, then you would get 0 if false, or 3/5 if true;
     * For example, you entered correctly 1 out of 3 phrases in FBMQ, then you would get 0 if false, or 1/3 if true;
     */
    @Column(name = "is_partial")
    protected boolean partialResponseAllowed;

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

    public void addResource(ua.edu.ratos.dao.entity.Resource resource) {
        this.resources.add(resource);
    }

    public void removeResource(ua.edu.ratos.dao.entity.Resource resource) {
        this.resources.remove(resource);
    }

    public Optional<Set<Help>> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<Set<ua.edu.ratos.dao.entity.Resource>> getResources() {
        return Optional.ofNullable(resources);
    }

    public boolean isValid() {
        if (this.question == null) return false;
        if (this.question.isEmpty()) return false;
        if (this.level < 1 || this.level > 3) return false;
        return true;
    }

    /**
     * Converts this question to dao model for representing in a learning session;
     * @return
     */
    public abstract ua.edu.ratos.service.session.domain.question.Question toDomain();

    public Optional<ua.edu.ratos.service.session.domain.Help> helpToDomain() {
        ua.edu.ratos.service.session.domain.Help help = null;
        if (this.help!=null && !this.help.isEmpty()) {
            Help helpEntity = this.help.iterator().next();
            return Optional.of(helpEntity.toDomain());
        }
        return Optional.of(help);
    }

    public Set<Resource> resourcesToDomain() {
        Set<Resource> resources = new HashSet<>();
        Set<ua.edu.ratos.dao.entity.Resource> resourceEntity = this.resources;
        if (resourceEntity != null && !resourceEntity.isEmpty()) {
            resourceEntity.forEach(r -> resources.add(r.toDomain()));
        }
        return resources;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                '}';
    }
}
