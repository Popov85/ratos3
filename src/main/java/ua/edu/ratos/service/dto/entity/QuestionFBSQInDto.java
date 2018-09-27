package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@ToString(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFBSQInDto extends QuestionInDto {

    @NotNull(message = "{dto.object.required}")
    private @Valid AnswerFBSQInDto answer;
}
