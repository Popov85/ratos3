package ua.edu.ratos.service.session.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.dto.answer.AnswerMQOutDto;

import java.util.Objects;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerMQ {

    private Long answerId;

    private Phrase leftPhrase;

    private Phrase rightPhrase;

    public AnswerMQOutDto toDto() {
        return new AnswerMQOutDto()
                .setAnswerId(answerId)
                .setLeftPhrase(leftPhrase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMQ answerMQ = (AnswerMQ) o;
        return Objects.equals(answerId, answerMQ.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
