package ua.edu.ratos.service.dto.entity;

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
    @Null(message = "{dto.pk.nullable}")
    private Long schemeThemeId;

    @Positive(message = "Invalid schemeId, {dto.fk.required}")
    private long schemeId;

    @Positive(message = "Invalid themeId, {dto.fk.required}")
    private long themeId;

    @PositiveOrZero(message = "Invalid order, {dto.value.positiveOrZero}")
    private short order;

    /**
     * Specifies if this is the first Theme to be associated with the Scheme
     */
    private boolean first;

    @NotEmpty(groups = {SchemeThemeSettingsInDto.Include.class}, message = "Invalid schemeThemeSettings, {dto.collection.required}")
    @Size(groups = {SchemeThemeSettingsInDto.Include.class}, min = 1, max = 5, message = "Invalid schemeThemeSettings, {dto.collection.invalid}")
    private Set<@Valid SchemeThemeSettingsInDto> schemeThemeSettings;
}
