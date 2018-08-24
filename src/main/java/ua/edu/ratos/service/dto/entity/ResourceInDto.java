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

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "Non-null resourceId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "Invalid resourceId, {dto.pk.required}")
    private Long resourceId;

    @NotBlank(groups = {New.class, Update.class,}, message = "Invalid link, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 5, max = 1000, message = "Invalid link, {dto.string.invalid}")
    private String link;

    @NotBlank(groups = {New.class, Update.class,}, message = "Invalid description, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 200, message = "Invalid description, {dto.string.invalid}")
    private String description;

    @Positive(groups = {QuestionInDto.New.class, QuestionInDto.Update.class}, message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
}
