package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerMCQInDto {

    public interface New{}
    public interface Update{}
    public interface Include{}

    @Null(groups = {AnswerMQInDto.New.class, AnswerSQInDto.Include.class}, message = "{dto.pk.invalid}")
    @NotNull(groups = {AnswerMQInDto.Update.class}, message = "{dto.pk.required}")
    private Long answerId;

    @NotEmpty(message = "Invalid answer, {dto.string.required}")
    @Size(min = 1, max = 100, message = "{dto.string.invalid}")
    private String answer;

    @Range(min = 0, max=100, message = "Invalid percent, {dto.range.invalid}")
    private short percent;

    // TODO 0 percent cannot be required!
    private boolean isRequired;

    @PositiveOrZero(message = "Invalid resourceId, {dto.fk.invalidOptional}")
    private long resourceId;

    @Null(groups = {AnswerSQInDto.Include.class})
    @NotNull(groups = {AnswerSQInDto.New.class, AnswerSQInDto.Update.class}, message = "{dto.pk.invalid}")
    @Positive(groups = {AnswerSQInDto.New.class, AnswerSQInDto.Update.class},message = "Invalid questionId, {dto.fk.invalid}")
    private Long questionId;
}
