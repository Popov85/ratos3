package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchemeThemeInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long schemeThemeId;

    @Positive(message = "{dto.fk.required}")
    private long schemeId;

    @Positive(message = "{dto.fk.required}")
    private long themeId;

    @PositiveOrZero(message = "{dto.value.positiveOrZero}")
    private short order;

    /**
     * Specifies if this is the first ThemeDomain to be associated with the SchemeDomain
     */
    private boolean first;

    @NotEmpty(groups = {SchemeThemeSettingsInDto.Include.class}, message = "{dto.collection.required}")
    @Size(groups = {SchemeThemeSettingsInDto.Include.class}, min = 1, max = 5, message = "{dto.collection.invalid}")
    private Set<@Valid SchemeThemeSettingsInDto> schemeThemeSettings;
}
