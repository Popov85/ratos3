package ua.edu.ratos.service.session.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Resource;
import ua.edu.ratos.service.session.domain.Theme;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public abstract class QuestionOutDto {

    protected Long questionId;

    protected String question;

    protected byte level;

    protected long type;

    protected String lang;

    protected Theme theme;

    protected boolean partialResponseAllowed;

    /**
     * Specifies whether Help exists for this question;
     * if true and if the settings permit it, student can request help
     */
    protected boolean helpAvailable;

    // Nullable (empty-able)
    protected Set<Resource> resources = new HashSet<>();

    @JsonIgnore
    public abstract boolean isShufflingSupported();

    public abstract void shuffle(CollectionShuffler collectionShuffler);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionOutDto that = (QuestionOutDto) o;
        return Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
