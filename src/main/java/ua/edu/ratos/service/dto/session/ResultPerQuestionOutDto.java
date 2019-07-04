package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultPerQuestionOutDto {

    private QuestionSessionOutDto question;

    private Response response;

    // a number [0-100]
    private double score;

    //-- Optional values--

    // Add level bounty if any
    private Double bounty;

    // Add penalty if any
    private Double penalty;
}
