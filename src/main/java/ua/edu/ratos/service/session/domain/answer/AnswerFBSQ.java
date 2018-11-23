package ua.edu.ratos.service.session.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.domain.SettingsFB;
import ua.edu.ratos.service.session.dto.answer.AnswerFBSQOutDto;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AnswerFBSQ {

    private Long answerId;

    private SettingsFB settings;

    private Set<Phrase> acceptedPhrases = new HashSet<>();

    public AnswerFBSQOutDto toDto() {
        return new AnswerFBSQOutDto()
                .setAnswerId(answerId)
                .setSettings(settings);
    }

}
