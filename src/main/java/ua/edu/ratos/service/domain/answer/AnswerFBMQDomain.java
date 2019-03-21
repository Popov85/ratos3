package ua.edu.ratos.service.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.dto.session.answer.AnswerFBMQSessionOutDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerFBMQDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long answerId;

    private String phrase;

    private byte occurrence = 1;

    private SettingsFBDomain settings;

    private Set<PhraseDomain> acceptedPhraseDomains = new HashSet<>();

    public AnswerFBMQSessionOutDto toDto() {
        return new AnswerFBMQSessionOutDto()
                .setAnswerId(answerId)
                .setPhrase(phrase)
                .setOccurrence(occurrence)
                .setSettings(settings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerFBMQDomain that = (AnswerFBMQDomain) o;
        return Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
