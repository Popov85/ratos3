package ua.edu.ratos.service.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.answer.AnswerMQDomain;
import ua.edu.ratos.service.dto.out.answer.CorrectAnswerMQOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionMQSessionOutDto;
import ua.edu.ratos.service.domain.response.ResponseMQ;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionMQDomain extends QuestionDomain {

    private Set<AnswerMQDomain> answers = new HashSet<>();

    public void addAnswer(AnswerMQDomain answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerMQDomain answer) {
        this.answers.remove(answer);
    }

    /**
     * If allowed, not strict match, each match contributes to the resulting score!
     * @param response
     * @return result of evaluation
     */
    public double evaluate(@NonNull final ResponseMQ response) {
        final Set<ResponseMQ.Triple> responses = response.getMatchedPhrases();
        if (responses==null || responses.isEmpty()) return 0;
        // Traverse through all the answers of this question
        int matchCounter = 0;
        int totalMatches = answers.size();
        for (AnswerMQDomain answer : answers) {
            final Long answerId = answer.getAnswerId();
            final Optional<ResponseMQ.Triple> responseMatcher = responses
                    .stream()
                    .filter(r -> answerId.equals(r.getAnswerId()))
                    .findFirst();
            if (responseMatcher.isPresent()) {
                final Long correctLeftPhraseId = answer.getLeftPhraseDomain().getPhraseId();
                final Long correctRightPhraseId = answer.getRightPhraseDomain().getPhraseId();
                final Long responseLeftPhraseId = responseMatcher.get().getLeftPhraseId();
                final Long responseRightPhraseId = responseMatcher.get().getRightPhraseId();
                if (correctLeftPhraseId.equals(responseLeftPhraseId) && correctRightPhraseId.equals(responseRightPhraseId))
                    matchCounter++;
            }// else consider this matcher as incorrect, go to the next answer (matcher)
        }
        // Get evaluating settings
        if (!this.partialResponseAllowed && matchCounter<totalMatches) return 0;
        return matchCounter*100d/totalMatches;
    }

    @Override
    @JsonIgnore
    public CorrectAnswerMQOutDto getCorrectAnswer() {
        Set<CorrectAnswerMQOutDto.Triple> correctPhrases = this.answers.stream().map(a -> new CorrectAnswerMQOutDto
                .Triple(a.getAnswerId(), a.getLeftPhraseDomain().getPhraseId(), a.getRightPhraseDomain().getPhraseId()))
                .collect(Collectors.toSet());
        return new CorrectAnswerMQOutDto(correctPhrases);
    }

    @Override
    public QuestionMQSessionOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMQSessionOutDto dto = modelMapper.map(this, QuestionMQSessionOutDto.class);
        dto.setLeftPhrases(new HashSet<>()).setLeftPhrases(new HashSet<>());
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        answers.forEach(a->dto.addLeftPhrase(a.toDto()));
        answers.forEach(a->dto.addRightPhrase(a.getRightPhraseDomain()));
        return dto;
    }
}
