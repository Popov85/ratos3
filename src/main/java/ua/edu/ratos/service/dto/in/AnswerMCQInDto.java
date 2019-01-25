package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerMCQInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long answerId;

    @NotBlank( message = "{dto.string.required}")
    @Size( min = 1, max = 500, message = "{dto.string.invalid}")
    private String answer;

    @Range(min = 0, max=100, message = "{dto.range.invalid}")
    private short percent;

    /**
     * 0 percent cannot be required! See custom validator for details
     * @see ua.edu.ratos.service.validator.AnswerMCQInDtoValidator
     */
    private boolean isRequired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero( message = " {dto.fk.optional}")
    private long resourceId;


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
