package ua.edu.ratos.service.dto.out.question;

import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.out.answer.AnswerSQOutDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
public class QuestionSQOutDto extends QuestionOutDto {

    private List<AnswerSQOutDto> answers = new ArrayList<>();

    public void addAnswer(AnswerSQOutDto answer) {
        this.answers.add(answer);
    }
}
