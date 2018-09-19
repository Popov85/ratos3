package ua.edu.ratos.service.dto.entity;

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
public class AcceptedPhraseInDto {

    @NotBlank(message = "Invalid phrase, {dto.string.required}")
    @Size(min = 1, max = 100, message = "Invalid phrase, {dto.string.invalid}")
    private String phrase;

    @Positive(message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
}
