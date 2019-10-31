package ua.edu.ratos.service.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBSQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.dto.out.answer.CorrectAnswerFBSQOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionFBSQSessionOutDto;
import ua.edu.ratos.service.domain.response.ResponseFBSQ;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(callSuper = true)
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
        String enteredPhrase = response.getEnteredPhrase();
        if (enteredPhrase==null || enteredPhrase.isEmpty()) return 0;
        final String enteredPhraseTrimmed = enteredPhrase.trim();
        List<String> acceptedPhrases = answer.getAcceptedPhraseDomains()
                .stream()
                .map(p -> p.getPhrase())
                .collect(Collectors.toList());
        // Normal process
        if (acceptedPhrases.contains(enteredPhraseTrimmed)) return 100;
        // Process case sensitivity
        SettingsFBDomain settings = answer.getSettings();
        if (!settings.isCaseSensitive()) {
            if (settings.checkCaseInsensitiveMatch(enteredPhraseTrimmed, acceptedPhrases)) return 100;
        }
        // Process typos
        if (settings.isTypoAllowed()) {
            if (settings.checkSingleTypoMatch(enteredPhraseTrimmed, acceptedPhrases)) return 100;
        }
        return 0;
    }

    @Override
    @JsonIgnore
    public CorrectAnswerFBSQOutDto getCorrectAnswer() {
        Set<String> acceptedPhrases = this.answer.getAcceptedPhraseDomains()
                .stream()
                .map(p -> p.getPhrase())
                .collect(Collectors.toSet());
        return new CorrectAnswerFBSQOutDto(acceptedPhrases);
    }

    @Override
    public QuestionFBSQSessionOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBSQSessionOutDto dto = modelMapper.map(this, QuestionFBSQSessionOutDto.class);
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        dto.setAnswer(answer.toDto());
        return dto;
    }
}
