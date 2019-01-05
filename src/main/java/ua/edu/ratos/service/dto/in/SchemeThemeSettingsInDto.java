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

    public interface NewAndUpdate{}
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long schemeThemeSettingsId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = Include.class, message = "{dto.fk.nullable}")
    @Positive(groups = NewAndUpdate.class, message = "{dto.fk.required}")
    private long schemeThemeId;

    @Positive(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.required}")
    private long questionTypeId;

    @PositiveOrZero(groups = {NewAndUpdate.class, Include.class}, message = "{dto.value.positiveOrZero}")
    private short level1;

    @PositiveOrZero(groups = {NewAndUpdate.class, Include.class}, message = "{dto.value.positiveOrZero}")
    private short level2;

    @PositiveOrZero(groups = {NewAndUpdate.class, Include.class}, message = "{dto.value.positiveOrZero}")
    private short level3;
}
