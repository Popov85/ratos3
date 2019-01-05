package ua.edu.ratos.service.dto.in;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long helpId;

    @NotBlank( message = "{dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String name;

    @NotBlank(message = "Invalid name, {dto.string.required}")
    @Size(min = 1, max = 1000, message = "{dto.string.invalid}")
    private String help;

    @Positive(message = "{dto.fk.required}")
    private long staffId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = "{dto.fk.optional}")
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
