package ua.edu.ratos.service.dto.out.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.PhraseOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerMQOutDto {

    private Long answerId;

    private PhraseOutDto leftPhrase;

    private PhraseOutDto rightPhrase;
}

