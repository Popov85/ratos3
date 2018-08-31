package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchemeThemeSettingsInDto {
    public interface New{}
    public interface Update{}
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class, Include.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long schemeThemeSettingsId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {Include.class}, message = "Invalid schemeThemeId, {dto.fk.nullable}")
    @Positive(groups = {New.class, Update.class}, message = "Invalid schemeThemeId, {dto.fk.required}")
    private long schemeThemeId;

    @Positive(groups = {New.class, Update.class, Include.class}, message = "Invalid questionTypeId, {dto.fk.required}")
    private long questionTypeId;

    @PositiveOrZero(groups = {New.class, Update.class, Include.class}, message = "Invalid level1, {dto.value.positiveOrZero}")
    private short level1;

    @PositiveOrZero(groups = {New.class, Update.class, Include.class}, message = "Invalid level2, {dto.value.positiveOrZero}")
    private short level2;

    @PositiveOrZero(groups = {New.class, Update.class, Include.class}, message = "Invalid level3, {dto.value.positiveOrZero}")
    private short level3;
}
