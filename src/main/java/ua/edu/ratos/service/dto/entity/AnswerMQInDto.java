package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerMQInDto {

    public interface New{}
    public interface Update{}

    @Null(groups = {AnswerMQInDto.New.class}, message = "{dto.pk.invalid}")
    @NotNull(groups = {AnswerMQInDto.Update.class}, message = "{dto.pk.required}")
    private Long answerId;

    @NotEmpty(message = "{dto.phrase.required}")
    @Size(min = 1, max = 100, message = "{dto.phrase.invalid}")
    private String leftPhrase;

    @NotEmpty(message = "{dto.phrase.required}")
    @Size(min = 1, max = 100, message = "{dto.phrase.invalid}")
    private String rightPhrase;

    @PositiveOrZero(message = "Invalid resourceId, {dto.fk.invalidOptional}")
    private long resourceId;

    @Positive(message = "Invalid questionId, {dto.fk.invalid}")
    private long questionId;
}
