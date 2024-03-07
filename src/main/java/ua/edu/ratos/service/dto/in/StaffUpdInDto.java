package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Optional;

@Getter
@Setter
@ToString
public class StaffUpdInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long staffId;

    private @Valid UserUpdInDto user;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 5, max = 30, message = "{dto.string.invalid}")
    private String role;

    @NotNull(message = "dto.fk.required")
    @Positive(message = "{dto.fk.required}")
    private Long positionId;

    public Optional<@Positive(message = "{dto.fk.required}") Long> depId = Optional.empty();

    private boolean active;
}
