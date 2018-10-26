package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.domain.entity.Language;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerFBMQOutDto {

    private Long answerId;

    private String phrase;

    private byte occurrence;

    protected Language lang;

    private short wordsLimit;

    private short symbolsLimit;

    private boolean isNumeric;

    private boolean isCaseSensitive;
}
