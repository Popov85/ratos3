package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionFBSQOutDto extends QuestionOutDto{

    private AnswerFBSQOutDto answer;
}
