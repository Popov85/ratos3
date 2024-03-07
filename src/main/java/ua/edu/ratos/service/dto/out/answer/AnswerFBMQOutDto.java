package ua.edu.ratos.service.dto.out.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.PhraseOutDto;
import ua.edu.ratos.service.dto.out.SettingsFBOutDto;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerFBMQOutDto {

    private Long answerId;

    private String phrase;

    private byte occurrence;

    private SettingsFBOutDto settings;

    private Set<PhraseOutDto> acceptedPhrases;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerFBMQOutDto that = (AnswerFBMQOutDto) o;
        return Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
