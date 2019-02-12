package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class LMSInDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long lmsId;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String name;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 20, max = 1000, message = "{dto.string.invalid}")
    private String key;

    @NotBlank(message = "{dto.string.required}")
    @Size(min = 20, max = 1000, message = "{dto.string.invalid}")
    private String secret;

    @Positive(message = "{dto.fk.required}")
    private long versionId;

    @NotEmpty(message = "{dto.collection.required}")
    private Set<String> origins = new HashSet<>();
}
