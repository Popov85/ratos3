package ua.edu.ratos.service.session.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Help;
import ua.edu.ratos.service.session.domain.Resource;
import ua.edu.ratos.service.session.domain.Theme;
import ua.edu.ratos.service.session.dto.question.QuestionOutDto;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * This DTO fully represents Question with correct answers for evaluating purposes
 * But this version is JSON-serializable and can be stored in DB as text within SessionData
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public abstract class Question {

    protected Long questionId;

    protected String question;

    protected byte level;

    protected long type;

    protected String lang;

    protected Theme theme;

    protected boolean partialResponseAllowed;

    protected Help help;

    protected Set<Resource> resources = new HashSet<>();

    @JsonIgnore
    public Optional<Help> getHelp() {
        return Optional.ofNullable(help);
    }

    @JsonIgnore
    public Optional<Set<Resource>> getResources() {
        return Optional.ofNullable(resources);
    }

    @JsonProperty("help")
    private Help getHelpOrNothing() {
        return getHelp().orElse(null);
    }

    @JsonProperty("resources")
    private Set<Resource> getResourcesOrNothing() {
        return getResources().orElse(null);
    }

    public abstract QuestionOutDto toDto();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionId, question.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
