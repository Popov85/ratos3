package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SchemeThemeInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long schemeId;

    @Positive(message = "{dto.value.positiveOrZero}")
    private long themeId;

    @PositiveOrZero(message = "{dto.value.positiveOrZero}")
    private short order;

    @NotEmpty(message = "{dto.collection.required}")
    @Size(min = 1, max = 5, message = "{dto.collection.invalid}")
    private Set<@Valid SchemeThemeSettingsInDto> settings;
}
