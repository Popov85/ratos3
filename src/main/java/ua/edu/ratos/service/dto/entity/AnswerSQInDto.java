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

    public interface New{}
    public interface Update{}
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class, Include.class}, message = "Non-null answerId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "Invalid answerid, {dto.pk.required}")
    private Long answerId;

    @Size(groups = {New.class, Update.class, Include.class}, min = 1, max = 100, message = "Invalid phrase, {dto.string.invalid}")
    private String phrase;

    @Range(groups = {New.class, Update.class, Include.class}, min = 0, max=20, message = "Invalid elementOrder, {dto.range.invalid}")
    private short order;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {New.class, Update.class, Include.class}, message = "Invalid resourceId, {dto.fk.optional}")
    private long resourceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "Non-null questionId, {dto.fk.nullable}")
    @NotNull(groups = {New.class, Update.class}, message = "Non-null questionId, {dto.fk.nullable}")
    @Positive(groups = {New.class, Update.class},message = "Invalid questionId, {dto.fk.required}")
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerSQInDto that = (AnswerSQInDto) o;
        return Objects.equals(phrase, that.phrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phrase);
    }
}
