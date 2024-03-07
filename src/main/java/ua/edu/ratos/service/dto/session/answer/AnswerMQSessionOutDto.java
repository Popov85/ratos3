package ua.edu.ratos.service.dto.session.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerMQSessionOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long answerId;

    private PhraseDomain leftPhraseDomain;
}

