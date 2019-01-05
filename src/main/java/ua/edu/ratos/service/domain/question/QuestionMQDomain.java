package ua.edu.ratos.service.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.answer.AnswerMQDomain;
import ua.edu.ratos.service.dto.session.question.QuestionMQOutDto;
import ua.edu.ratos.service.domain.response.ResponseMQ;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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
    public double evaluate(ResponseMQ response) {
        final Set<ResponseMQ.Triple> responses = response.getMatchedPhrases();
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
        // Get evaluating settingsDomain
        if (!this.partialResponseAllowed && matchCounter<totalMatches) return 0;
        return matchCounter*100d/totalMatches;
    }

    @Override
    public QuestionMQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMQOutDto dto = modelMapper.map(this, QuestionMQOutDto.class);
        dto.setLeftPhrases(new HashSet<>()).setLeftPhrases(new HashSet<>());
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        answers.forEach(a->dto.addLeftPhrase(a.toDto()));
        answers.forEach(a->dto.addRightPhrase(a.getRightPhraseDomain()));
        return dto;
    }
}
