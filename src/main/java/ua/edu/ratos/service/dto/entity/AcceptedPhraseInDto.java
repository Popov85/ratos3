package ua.edu.ratos.service.dto.entity;

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

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String phrase;

    @Positive(message = "{dto.fk.required}")
    private long staffId;
}
