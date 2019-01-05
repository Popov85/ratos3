package ua.edu.ratos.service.domain.question;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBSQDomain;
import ua.edu.ratos.service.dto.session.question.QuestionFBSQOutDto;
import ua.edu.ratos.service.domain.response.ResponseFBSQ;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionFBSQDomain extends QuestionDomain {

    private AnswerFBSQDomain answer;

    /**
     * Strict comparison: if entered phraseDomain is equal to any of the accepted phrases, then correct 100%
     * otherwise - not correct 0%
     * @param response
     * @return result of evaluation 0 or 100
     */
    public int evaluate(@NonNull final ResponseFBSQ response) {
        final String enteredPhrase = response.getEnteredPhrase().trim();
        List<String> acceptedPhrases = answer.getAcceptedPhraseDomains()
                .stream()
                .map(p -> p.getPhrase())
                .collect(Collectors.toList());
        // Normal process
        if (acceptedPhrases.contains(enteredPhrase)) return 100;
        // Process case sensitivity
        SettingsFBDomain settings = answer.getSettings();
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
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        dto.setAnswer(answer.toDto());
        return dto;
    }
}
