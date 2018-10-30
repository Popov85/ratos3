package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.Resource;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerSQOutDto {

    private Long answerId;

    private Phrase phrase;
}
