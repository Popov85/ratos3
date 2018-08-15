package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@ToString(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFBMQInDto extends QuestionInDto {

    @NotEmpty(groups = {QuestionInDto.New.class}, message = "Invalid answers, {dto.collection.required}")
    @Size(groups = {QuestionInDto.New.class}, min = 1, max = 20, message = "Invalid answers, {dto.collection.invalid}")
    private Set<@Valid AnswerFBMQInDto> answers;
}
