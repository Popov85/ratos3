package ua.edu.ratos.service.session.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.session.domain.answer.AnswerMCQ;
import ua.edu.ratos.service.session.dto.question.QuestionMCQOutDto;
import ua.edu.ratos.service.session.domain.response.ResponseMultipleChoice;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionMCQ extends Question {

    /**
     * With the single correct answer?
     */
    private boolean isSingle;

    private Set<AnswerMCQ> answers = new HashSet<>();

    public void addAnswer(AnswerMCQ answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerMCQ answer) {
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
    public int evaluate(ResponseMultipleChoice response) {
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
            for (AnswerMCQ answer : this.answers) {
                if (responseId.equals(answer.getAnswerId())) result+=answer.getPercent();
            }
        }
        return result;
    }

    private List<Long> getZeroAnswers() {
        List<Long> zeroAnswers = this.answers
                .stream()
                .filter(answer -> answer.getPercent() == 0)
                .map(AnswerMCQ::getAnswerId)
                .collect(Collectors.toList());
        return zeroAnswers;
    }

    private List<Long> getRequiredAnswers() {
        List<Long> requiredAnswers = new ArrayList<>();
        Set<AnswerMCQ> answers = this.answers;
        requiredAnswers.addAll(answers
                .stream()
                .filter(AnswerMCQ::isRequired)
                .map(AnswerMCQ::getAnswerId)
                .collect(Collectors.toList()));
        return requiredAnswers;
    }

    @Override
    public QuestionMCQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMCQOutDto dto = modelMapper.map(this, QuestionMCQOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelp().isPresent()) ? true : false);
        dto.setResources((getResources().isPresent()) ? getResources().get() : null);
        answers.forEach(a->dto.addAnswer(a.toDto()));
        return dto;
    }
}
