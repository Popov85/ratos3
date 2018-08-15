package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInDto {

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "Non-null questionId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "Invalid questionId, {dto.pk.required}")
    private Long questionId;

    @Size(groups = {New.class, Update.class}, min = 1, max = 1000, message = "Invalid question, {dto.string.invalid}")
    private String question;

    @Range(groups = {New.class, Update.class}, min=1, max=3, message = "Invalid level, {dto.range.invalid}")
    private byte level;

    @Positive(groups = {New.class, Update.class}, message = "Invalid themeId, {dto.fk.required}")
    private long themeId;

    @Positive(groups = {New.class, Update.class}, message = "Invalid questionTypeId, {dto.fk.required}")
    @Range(min=1, max=5, message = "Currently only 5 question types are supported, {dto.range.invalid}")
    private long questionTypeId;

    @Positive(groups = {New.class, Update.class}, message = "Invalid langId, {dto.fk.required}")
    private long langId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {New.class, Update.class}, message = "Invalid helpId, {dto.fk.optional}")
    private long helpId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(groups = {New.class, Update.class}, min = 1, max = 3, message = "Invalid resourcesIds, {dto.collection.invalid}")
    private Set<Long> resourcesIds;
}
