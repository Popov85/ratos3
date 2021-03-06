package ua.edu.ratos.service.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.dto.session.answer.AnswerSQSessionOutDto;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerSQDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long answerId;

    private PhraseDomain phraseDomain;

    private short order;

    public AnswerSQSessionOutDto toDto() {
        return new AnswerSQSessionOutDto()
                .setAnswerId(answerId)
                .setPhraseDomain(phraseDomain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerSQDomain answerSQDomain = (AnswerSQDomain) o;
        return Objects.equals(answerId, answerSQDomain.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
