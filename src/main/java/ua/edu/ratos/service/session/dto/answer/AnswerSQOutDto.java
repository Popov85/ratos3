package ua.edu.ratos.service.session.dto.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerSQOutDto {

    private Long answerId;

    private Phrase phrase;
}
