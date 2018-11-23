package ua.edu.ratos.service.session.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.session.domain.answer.AnswerSQ;
import ua.edu.ratos.service.session.dto.question.QuestionSQOutDto;
import ua.edu.ratos.service.session.domain.response.ResponseSequence;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionSQ extends Question {
    private Set<AnswerSQ> answers = new HashSet<>();

    public void add(AnswerSQ answer) {
        this.answers.add(answer);
    }

    /**
     * We only compare list of answers (to contain all the elements and to be in the right order of elements)
     * Strict match of sequences: correct and obtained sequence must be identical, {146, 222, 281, 300, 312} = {146, 222, 281, 300, 312}
     * If at least one mis-order or mis-match - result is fully incorrect  response (0)
     * @param response
     * @return
     */
    public int evaluate(ResponseSequence response) {
        final List<Long> responseSequence = response.getAnswerIds();
        if (responseSequence==null || responseSequence.isEmpty()) return 0;
        if (responseSequence.equals(findAll())) return 100;
        return 0;
    }

    private List<Long> findAll() {
        return answers.stream()
                .sorted(Comparator.comparingInt(AnswerSQ::getOrder))
                .map(AnswerSQ::getAnswerId)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionSQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionSQOutDto dto = modelMapper.map(this, QuestionSQOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelp().isPresent()) ? true : false);
        dto.setResources((getResources().isPresent()) ? getResources().get() : null);
        answers.forEach(a->dto.addAnswer(a.toDto()));
        return dto;
    }
}
