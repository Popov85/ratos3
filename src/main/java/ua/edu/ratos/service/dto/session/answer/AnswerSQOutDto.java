package ua.edu.ratos.service.dto.session.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerSQOutDto {

    private Long answerId;

    private PhraseDomain phraseDomain;
}
