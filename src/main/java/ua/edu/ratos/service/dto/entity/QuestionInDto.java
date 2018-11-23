package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long questionId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 1000, message = "{dto.string.invalid}")
    private String question;

    @Range(min=1, max=3, message = "{dto.range.invalid}")
    private byte level;

    @Positive( message = "{dto.fk.required}")
    private long themeId;

    @Positive(message = "{dto.fk.required}")
    @Range(min=1, max=5, message = "{dto.range.invalid}")
    private long questionTypeId;

    @Positive(message = "{dto.fk.required}")
    private long langId;

    // Currently only one Help can be associated with a question
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = "{dto.fk.optional}")
    private long helpId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 1, max = 3, message = "{dto.collection.invalid}")
    private Set<Long> resourcesIds;
}
