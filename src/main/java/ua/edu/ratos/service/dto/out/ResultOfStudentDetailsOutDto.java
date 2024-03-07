package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.session.ResultPerQuestionOutDto;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultOfStudentDetailsOutDto {

    private Long resultId;

    // TODO: include more captured data about the student session

    private List<ResultPerQuestionOutDto> questionResults;
}
