package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.util.Objects;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HelpInDto {

    public interface New{}
    public interface Update{}

    @Null(groups = {HelpInDto.New.class}, message = "{dto.pk.invalid}")
    @NotNull(groups = {HelpInDto.Update.class}, message = "{dto.pk.required}")
    private Long helpId;

    @NotEmpty(groups = {HelpInDto.New.class, HelpInDto.Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {HelpInDto.New.class, HelpInDto.Update.class}, min = 1, max = 100, message = "Invalid name, {dto.string.invalid}")
    private String name;

    @NotEmpty(groups = {HelpInDto.New.class, HelpInDto.Update.class}, message = "Invalid help, {dto.string.required}")
    @Size(groups = {HelpInDto.New.class, HelpInDto.Update.class}, min = 1, max = 1000, message = "Invalid help, {dto.string.invalid}")
    private String help;

    @Positive(groups = {HelpInDto.New.class, HelpInDto.Update.class}, message = "Invalid staffId, {dto.fk.invalid}")
    private long staffId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {HelpInDto.New.class, HelpInDto.Update.class}, message = "Invalid rightPhraseResourceId, {dto.fk.invalidOptional}")
    private long resourceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpInDto helpInDto = (HelpInDto) o;
        return Objects.equals(help, helpInDto.help);
    }

    @Override
    public int hashCode() {
        return Objects.hash(help);
    }
}
