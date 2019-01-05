package ua.edu.ratos.service.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.HelpDomain;
import ua.edu.ratos.service.domain.ResourceDomain;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.dto.session.question.QuestionOutDto;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * This domain object fully represents Question with correct answers for evaluating purposes
 * But this version is JSON-serializable and can be stored in DB as text within SessionData
 */
@Setter
@Getter
@ToString(exclude = {"themeDomain", "helpDomain", "resourceDomains"})
@Accessors(chain = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public abstract class QuestionDomain {

    protected Long questionId;

    protected String question;

    protected byte level;

    protected long type;

    protected String lang;

    protected ThemeDomain themeDomain;

    protected boolean partialResponseAllowed;

    protected HelpDomain helpDomain;

    protected Set<ResourceDomain> resourceDomains = new HashSet<>();

    @JsonIgnore
    public Optional<HelpDomain> getHelpDomain() {
        return Optional.ofNullable(helpDomain);
    }

    @JsonIgnore
    public Optional<Set<ResourceDomain>> getResourceDomains() {
        return Optional.ofNullable(resourceDomains);
    }

    @JsonProperty("helpDomain")
    private HelpDomain getHelpOrNothing() {
        return getHelpDomain().orElse(null);
    }

    @JsonProperty("resourceDomains")
    private Set<ResourceDomain> getResourcesOrNothing() {
        return getResourceDomains().orElse(null);
    }

    /**
     * Transforms domain object to DTO object.
     * DTO object is different from domain object in that is hides correct answers as well as help
     * @return DTO question object for learning session
     */
    public abstract QuestionOutDto toDto();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDomain questionDomain = (QuestionDomain) o;
        return Objects.equals(questionId, questionDomain.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
