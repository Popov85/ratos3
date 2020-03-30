package ua.edu.ratos.service.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.answer.AnswerSQDomain;
import ua.edu.ratos.service.domain.response.Response;
import ua.edu.ratos.service.domain.response.ResponseSQ;
import ua.edu.ratos.service.dto.out.answer.CorrectAnswerSQOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSQSessionOutDto;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true)
public class QuestionSQDomain extends QuestionDomain {
    private Set<AnswerSQDomain> answers = new HashSet<>();

    public void add(AnswerSQDomain answer) {
        this.answers.add(answer);
    }

    /**
     * We only compare list of answers (to contain all the elements and to be in the right order of elements)
     * Strict match of sequences: correct and obtained sequence must be identical, {146, 222, 281, 300, 312} = {146, 222, 281, 300, 312}
     * If at least one mis-order or mis-match - result is fully incorrect  response (0)
     * @param response
     * @return
     */
    public double evaluate(@NonNull final Response response) {
        if (!(response instanceof ResponseSQ))
            throw new RuntimeException("Invalid Response type: ResponseSQ was expected!");
        final List<Long> responseSequence = ((ResponseSQ) response).getAnswerIds();
        if (responseSequence==null || responseSequence.isEmpty()) return 0;
        if (responseSequence.equals(findAll())) return 100;
        return 0;
    }

    private List<Long> findAll() {
        return answers.stream()
                .sorted(Comparator.comparingInt(AnswerSQDomain::getOrder))
                .map(AnswerSQDomain::getAnswerId)
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public CorrectAnswerSQOutDto getCorrectAnswer() {
        Set<CorrectAnswerSQOutDto.Triple> correctAnswers = this.answers
                .stream()
                .map(a -> new CorrectAnswerSQOutDto.Triple(a.getAnswerId(), a.getPhraseDomain().getPhraseId(), a.getOrder()))
                .collect(Collectors.toSet());
        return new CorrectAnswerSQOutDto(correctAnswers);
    }

    @Override
    public QuestionSQSessionOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionSQSessionOutDto dto = modelMapper.map(this, QuestionSQSessionOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResource((getResourceDomain().isPresent()) ? getResourceDomain().get() : null);
        answers.forEach(a->dto.addAnswer(a.toDto()));
        return dto;
    }
}
