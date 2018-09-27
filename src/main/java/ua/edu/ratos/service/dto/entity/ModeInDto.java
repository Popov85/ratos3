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
public class ModeInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long modeId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 2, max = 50, message = "{dto.string.invalid}")
    private String name;

    @Positive(message = "{dto.fk.required}")
    private long staffId;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean resultDetails;

    private boolean pauseable;

    private boolean preservable;

    private boolean reportable;

    private boolean deleted;
}
