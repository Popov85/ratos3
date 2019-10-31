package ua.edu.ratos.service.dto.session.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.ResourceDomain;
import ua.edu.ratos.service.domain.ThemeDomain;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public abstract class QuestionSessionOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long questionId;
    // Number of this question in the individual sequence
    protected int serialNumber;

    protected String question;

    protected byte level;

    protected long type;

    protected String lang;

    protected ThemeDomain themeDomain;

    protected boolean required;

    protected boolean partialResponseAllowed;

    /**
     * Specifies whether Help exists for this question;
     * if true and if the settings permit it, student can request Help
     */
    protected boolean helpAvailable;

    // Nullable (empty-able)
    protected Set<ResourceDomain> resourceDomains = new HashSet<>();

    @JsonIgnore
    public abstract boolean isShufflingSupported();

    public abstract void shuffle(CollectionShuffler collectionShuffler);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionSessionOutDto that = (QuestionSessionOutDto) o;
        return Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
