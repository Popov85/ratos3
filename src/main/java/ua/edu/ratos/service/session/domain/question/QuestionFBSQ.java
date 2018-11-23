package ua.edu.ratos.service.session.domain.question;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.session.domain.SettingsFB;
import ua.edu.ratos.service.session.domain.answer.AnswerFBSQ;
import ua.edu.ratos.service.session.dto.question.QuestionFBSQOutDto;
import ua.edu.ratos.service.session.domain.response.ResponseFillBlankSingle;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionFBSQ extends Question {

    private AnswerFBSQ answer;

    /**
     * Strict comparison: if entered phrase is equal to any of the accepted phrases, then correct 100%
     * otherwise - not correct 0%
     * @param response
     * @return result of evaluation 0 or 100
     */
    public int evaluate(@NonNull final ResponseFillBlankSingle response) {
        final String enteredPhrase = response.getEnteredPhrase().trim();
        List<String> acceptedPhrases = answer.getAcceptedPhrases()
                .stream()
                .map(p -> p.getPhrase())
                .collect(Collectors.toList());
        // Normal process
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        // Process case sensitivity
        SettingsFB settings = answer.getSettings();
        if (!settings.isCaseSensitive()) {
            if (settings.checkCaseInsensitiveMatch(enteredPhrase, acceptedPhrases)) return 100;
        }
        // Process typos
        if (settings.isTypoAllowed()) {
            if (settings.checkSingleTypoMatch(enteredPhrase, acceptedPhrases)) return 100;
        }
        return 0;
    }

    @Override
    public QuestionFBSQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBSQOutDto dto = modelMapper.map(this, QuestionFBSQOutDto.class);
        dto.setHelpAvailable((getHelp().isPresent()) ? true : false);
        dto.setResources((getResources().isPresent()) ? getResources().get() : null);
        dto.setAnswer(answer.toDto());
        return dto;
    }
}
