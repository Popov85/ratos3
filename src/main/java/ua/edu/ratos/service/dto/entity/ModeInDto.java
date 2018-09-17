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

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long modeId;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid name, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 2, max = 50, message = "Invalid name, {dto.string.invalid}")
    private String name;

    private boolean helpable;

    private boolean pyramid;

    private boolean skipable;

    private boolean rightAnswer;

    private boolean resultDetails;

    private boolean pauseable;

    private boolean preservable;

    private boolean reportable;

    private boolean deleted;

    @Positive(groups = {New.class, Update.class}, message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
}
