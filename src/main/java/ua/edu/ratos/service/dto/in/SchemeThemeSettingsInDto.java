package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchemeThemeSettingsInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long schemeThemeSettingsId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long schemeThemeId;

    @Positive( message = "{dto.fk.required}")
    private long questionTypeId;

    @PositiveOrZero( message = "{dto.value.positiveOrZero}")
    private short level1;

    @PositiveOrZero(message = "{dto.value.positiveOrZero}")
    private short level2;

    @PositiveOrZero(message = "{dto.value.positiveOrZero}")
    private short level3;
}
