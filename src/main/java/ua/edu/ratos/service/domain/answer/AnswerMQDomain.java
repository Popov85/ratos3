package ua.edu.ratos.service.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.dto.session.answer.AnswerMQSessionOutDto;

import java.util.Objects;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerMQDomain {

    private Long answerId;

    private PhraseDomain leftPhraseDomain;

    private PhraseDomain rightPhraseDomain;

    public AnswerMQSessionOutDto toDto() {
        return new AnswerMQSessionOutDto()
                .setAnswerId(answerId)
                .setLeftPhraseDomain(leftPhraseDomain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMQDomain answerMQDomain = (AnswerMQDomain) o;
        return Objects.equals(answerId, answerMQDomain.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
