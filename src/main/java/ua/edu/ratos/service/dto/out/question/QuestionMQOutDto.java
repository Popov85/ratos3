package ua.edu.ratos.service.dto.out.question;

import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.out.answer.AnswerMQOutDto;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class QuestionMQOutDto extends QuestionOutDto {

    private List<AnswerMQOutDto> answers = new ArrayList<>();

    public void addAnswer(AnswerMQOutDto answer) {
        this.answers.add(answer);
    }
}
