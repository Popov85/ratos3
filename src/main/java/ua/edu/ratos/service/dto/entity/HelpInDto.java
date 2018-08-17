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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long helpId;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 100, message = "Invalid name, {dto.string.invalid}")
    private String name;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 1000, message = "Invalid help, {dto.string.invalid}")
    private String help;

    @Positive(groups = {New.class, Update.class}, message = "Invalid staffId, {dto.fk.required}")
    private long staffId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {New.class, Update.class}, message = "Invalid resourceId, {dto.fk.optional}")
    private long resourceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpInDto helpInDto = (HelpInDto) o;
        return Objects.equals(name, helpInDto.name) &&
                Objects.equals(help, helpInDto.help);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, help);
    }
}
