package ua.edu.ratos.service.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.answer.AnswerMCQDomain;
import ua.edu.ratos.service.dto.session.question.QuestionMCQSessionOutDto;
import ua.edu.ratos.service.domain.response.ResponseMCQ;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(callSuper = true, exclude = {"answers"})
@Accessors(chain = true)
public class QuestionMCQDomain extends QuestionDomain {

    /**
     * With the single correct answer?
     */
    private boolean isSingle;

    private Set<AnswerMCQDomain> answers = new HashSet<>();

    public void addAnswer(AnswerMCQDomain answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerMCQDomain answer) {
        this.answers.remove(answer);
    }

    /**
     * 3-step algorithm:
     * 1. Check if response contains any WRONG answers, if so - the whole response count as incorrect;
     * 2. Check if response contains all REQUIRED answers, if not all - the whole response count as incorrect;
     * 3. Calculate the total score
     * @param response
     * @return result of evaluation: a number [0-100]
     */
    public int evaluate(ResponseMCQ response) {
        List<Long> zeroAnswers = getZeroAnswers();
        Set<Long> responseIds = response.getAnswerIds();
        for (Long responseId: responseIds) {
            if (zeroAnswers.contains(responseId)) return 0;
        }
        List<Long> requiredAnswers = getRequiredAnswers();
        for (Long requiredAnswer : requiredAnswers) {
            if(!responseIds.contains(requiredAnswer)) return 0;
        }
        int result = 0;
        for (Long responseId: responseIds) {
            for (AnswerMCQDomain answer : this.answers) {
                if (responseId.equals(answer.getAnswerId())) result+=answer.getPercent();
            }
        }
        return result;
    }

    private List<Long> getZeroAnswers() {
        List<Long> zeroAnswers = this.answers
                .stream()
                .filter(answer -> answer.getPercent() == 0)
                .map(AnswerMCQDomain::getAnswerId)
                .collect(Collectors.toList());
        return zeroAnswers;
    }

    private List<Long> getRequiredAnswers() {
        List<Long> requiredAnswers = new ArrayList<>();
        Set<AnswerMCQDomain> answers = this.answers;
        requiredAnswers.addAll(answers
                .stream()
                .filter(AnswerMCQDomain::isRequired)
                .map(AnswerMCQDomain::getAnswerId)
                .collect(Collectors.toList()));
        return requiredAnswers;
    }

    @Override
    public QuestionMCQSessionOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMCQSessionOutDto dto = modelMapper.map(this, QuestionMCQSessionOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        answers.forEach(a->dto.addAnswer(a.toDto()));
        return dto;
    }
}
