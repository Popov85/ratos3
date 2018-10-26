package ua.edu.ratos.service.dto.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ua.edu.ratos.service.dto.response.Response;

@Getter
@ToString
@AllArgsConstructor
public class ResultPerQuestionOutDto {

    private final Long questionId;

    private final String question;

    private final Response response;

    private final double score;
}
