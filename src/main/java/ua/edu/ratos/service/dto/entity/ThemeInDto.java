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
public class ThemeInDto {

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long themeId;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 100, message = "Invalid name, {dto.string.invalid}")
    private String name;

    @Positive(groups = {New.class, Update.class}, message = "Invalid courseId, {dto.fk.required}")
    private long courseId;
}
