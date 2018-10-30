package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.domain.entity.Resource;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerMQOutDto {

    private Long answerId;

    private Phrase leftPhrase;
}

