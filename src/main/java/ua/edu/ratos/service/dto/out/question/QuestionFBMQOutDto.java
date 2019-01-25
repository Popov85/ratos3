package ua.edu.ratos.service.dto.out.question;

import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.out.answer.AnswerFBMQOutDto;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString(callSuper = true)
public class QuestionFBMQOutDto extends QuestionOutDto {

    private Set<AnswerFBMQOutDto> answers = new HashSet<>();

    public void addAnswer(AnswerFBMQOutDto answer) {
        this.answers.add(answer);
    }
}
