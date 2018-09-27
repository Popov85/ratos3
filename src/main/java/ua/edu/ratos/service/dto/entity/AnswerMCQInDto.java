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
public class AnswerMCQInDto {

    public interface NewAndUpdate{}
    public interface Include{}

    private Long answerId;

    @NotBlank(groups = {NewAndUpdate.class, Include.class}, message = "{dto.string.required}")
    @Size(groups = {NewAndUpdate.class, Include.class}, min = 1, max = 500, message = "{dto.string.invalid}")
    private String answer;

    @Range(groups = {NewAndUpdate.class, Include.class}, min = 0, max=100, message = "{dto.range.invalid}")
    private short percent;
    /**
     * 0 percent cannot be required! See custom validator for details
     * @see ua.edu.ratos.service.dto.validator.AnswerMCQInDtoValidator
     */
    private boolean isRequired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {NewAndUpdate.class, Include.class}, message = " {dto.fk.optional}")
    private long resourceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "{dto.fk.nullable}")
    @Positive(groups = {NewAndUpdate.class},message = "{dto.fk.required}")
    private Long questionId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMCQInDto that = (AnswerMCQInDto) o;
        return Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}
