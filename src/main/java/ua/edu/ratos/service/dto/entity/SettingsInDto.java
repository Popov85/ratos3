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
public class SettingsInDto {

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long setId;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 2, max = 50, message = "Invalid name, {dto.string.invalid}")
    private String name;

    // if 0, not specified then not limited
    @PositiveOrZero(groups = {New.class, Update.class}, message = "Invalid secondsPerQuestion, {dto.value.positiveOrZero}")
    @Max(value = 600, groups = {New.class, Update.class}, message = "Invalid secondsPerQuestion, max=600")
    private int secondsPerQuestion;

    @Positive(groups = {New.class, Update.class}, message = "Invalid questionsPerSheet, {dto.value.positive}")
    @Max(value = 24, groups = {New.class, Update.class}, message = "Invalid questionsPerSheet, max=24")
    private short questionsPerSheet;

    @Positive(groups = {New.class, Update.class}, message = "Invalid daysKeepResultDetails, {dto.value.positive}")
    @Max(value = 365, groups = {New.class, Update.class}, message = "Invalid daysKeepResultDetails, max=365")
    private short daysKeepResultDetails;

    @Positive(groups = {New.class, Update.class}, message = "Invalid threshold3, {dto.value.positive}")
    @Max(value = 100, groups = {New.class, Update.class}, message = "Invalid threshold3, max=100")
    private byte threshold3;

    @Positive(groups = {New.class, Update.class}, message = "Invalid threshold4, {dto.value.positive}")
    @Max(value = 100, groups = {New.class, Update.class}, message = "Invalid threshold4, max=100")
    private byte threshold4;

    @Positive(groups = {New.class, Update.class}, message = "Invalid threshold5, {dto.value.positive}")
    @Max(value = 100, groups = {New.class, Update.class}, message = "Invalid threshold5, max=100")
    private byte threshold5;

    @Positive(groups = {New.class, Update.class}, message = "Invalid level2Coefficient, {dto.value.positive}")
    @Min(value = 1, groups = {New.class, Update.class}, message = "Invalid level2Coefficient, max=1")
    @Max(value = 10, groups = {New.class, Update.class}, message = "Invalid level2Coefficient, max=10")
    private float level2Coefficient;

    @Positive(groups = {New.class, Update.class}, message = "Invalid level3Coefficient, {dto.value.positive}")
    @Min(value = 1, groups = {New.class, Update.class}, message = "Invalid level3Coefficient, max=1")
    @Max(value = 10, groups = {New.class, Update.class}, message = "Invalid level3Coefficient, max=10")
    private float level3Coefficient;

    private boolean displayPercent;

    private boolean displayMark;

    @Positive(groups = {New.class, Update.class}, message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
}
