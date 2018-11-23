package ua.edu.ratos.service.session.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.dto.answer.AnswerSQOutDto;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerSQ {

    private Long answerId;

    private Phrase phrase;

    private short order;

    public AnswerSQOutDto toDto() {
        return new AnswerSQOutDto()
                .setAnswerId(answerId)
                .setPhrase(phrase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerSQ answerSQ = (AnswerSQ) o;
        return Objects.equals(answerId, answerSQ.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
