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
public class SchemeInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long schemeId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String name;

    @Positive(message = "{dto.fk.required}")
    private long strategyId;

    @Positive(message = "{dto.fk.required}")
    private long settingsId;

    @Positive(message = "{dto.fk.required}")
    private long modeId;

    @Positive(message = "{dto.fk.required}")
    private long courseId;

    @Positive(message = "{dto.fk.required}")
    private long staffId;

    private boolean active;

}
