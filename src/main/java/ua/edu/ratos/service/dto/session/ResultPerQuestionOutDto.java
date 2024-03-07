package ua.edu.ratos.service.dto.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;

/**
 * Used to return correct answer to a question during learning session
 * provided it is allowed by current mode!
 * Also, used to return outcome at the end of a session according to current settings.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultPerQuestionOutDto {

    private QuestionSessionOutDto question;

    // Student response
    private Response response;

    // For checks, included always;
    // For results, included only if the current mode
    // mode.isRightAnswer() is true.
    private Object correctAnswer;

    // a number [0-100]
    private double score;

    // Add level bounty if any
    private Double bounty;

    // Add penalty if any
    private Double penalty;
}
