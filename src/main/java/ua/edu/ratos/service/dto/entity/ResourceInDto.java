package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@ToString(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResourceInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long resourceId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 5, max = 1000, message = "{dto.string.invalid}")
    private String link;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 200, message = "{dto.string.invalid}")
    private String description;

    @Positive(message = "{dto.fk.required}")
    private long staffId;
}
