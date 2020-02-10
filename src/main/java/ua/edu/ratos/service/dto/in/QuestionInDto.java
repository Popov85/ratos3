package ua.edu.ratos.service.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionInDto {

    private Long questionId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 1000, message = "{dto.string.invalid}")
    private String question;

    @Range(min=1, max=3, message = "{dto.range.invalid}")
    private byte level;

    @Positive( message = "{dto.fk.required}")
    private long themeId;

    // Currently only one Help can be associated with a question
    public Optional<@Positive(message = "{dto.fk.optional}") Long> helpId = Optional.empty();

    // Currently only one Resource can be associated with a question
    public Optional<@Positive(message = "{dto.fk.optional}") Long> resourceId = Optional.empty();

    // Mark the flag as true to guarantee that the question will
    // appear in each learning session!
    private boolean required;
}
