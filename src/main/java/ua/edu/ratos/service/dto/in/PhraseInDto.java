package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PhraseInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long phraseId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String phrase;

    @Positive(message = "{dto.fk.required}")
    private long staffId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = "{dto.fk.optional}")
    private Long resourceId;
}
