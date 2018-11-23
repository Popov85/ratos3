package ua.edu.ratos.service.session.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.domain.SettingsFB;
import ua.edu.ratos.service.session.dto.answer.AnswerFBMQOutDto;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerFBMQ {

    private Long answerId;

    private String phrase;

    private byte occurrence = 1;

    private SettingsFB settings;

    private Set<Phrase> acceptedPhrases = new HashSet<>();

    public AnswerFBMQOutDto toDto() {
        return new AnswerFBMQOutDto()
                .setAnswerId(answerId)
                .setPhrase(phrase)
                .setOccurrence(occurrence)
                .setSettings(settings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerFBMQ that = (AnswerFBMQ) o;
        return Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
