package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;
import java.util.Objects;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSQInDto {

    public interface NewAndUpdate{}
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long answerId;

    @Positive(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.required}")
    private Long phraseId;

    @Range(groups = {NewAndUpdate.class, Include.class}, min = 0, max=20, message = "{dto.range.invalid}")
    private short order;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = Include.class, message = "{dto.fk.nullable}")
    @NotNull(groups = NewAndUpdate.class, message = "{dto.fk.nullable}")
    @Positive(groups = NewAndUpdate.class, message = "{dto.fk.required}")
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerSQInDto that = (AnswerSQInDto) o;
        return Objects.equals(phraseId, that.phraseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phraseId);
    }
}
