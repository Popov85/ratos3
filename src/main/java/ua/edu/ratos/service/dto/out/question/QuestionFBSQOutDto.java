package ua.edu.ratos.service.dto.out.question;

import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.out.answer.AnswerFBSQOutDto;

@Getter
@ToString(callSuper = true)
public class QuestionFBSQOutDto extends QuestionOutDto {

    private AnswerFBSQOutDto answer;

    public void addAnswer(AnswerFBSQOutDto answer) {
        this.answer = answer;
    }
}
