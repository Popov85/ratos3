package ua.edu.ratos.service.session.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.session.domain.answer.AnswerMQ;
import ua.edu.ratos.service.session.dto.question.QuestionMQOutDto;
import ua.edu.ratos.service.session.domain.response.ResponseMatcher;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionMQ extends Question {

    private Set<AnswerMQ> answers = new HashSet<>();

    public void addAnswer(AnswerMQ answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerMQ answer) {
        this.answers.remove(answer);
    }

    /**
     * If allowed, not strict match, each match contributes to the resulting score!
     * @param response
     * @return result of evaluation
     */
    public double evaluate(ResponseMatcher response) {
        final Set<ResponseMatcher.Triple> responses = response.getMatchedPhrases();
        // Traverse through all the answers of this question
        int matchCounter = 0;
        int totalMatches = answers.size();
        for (AnswerMQ answer : answers) {
            final Long answerId = answer.getAnswerId();
            final Optional<ResponseMatcher.Triple> responseMatcher = responses
                    .stream()
                    .filter(r -> answerId.equals(r.getAnswerId()))
                    .findFirst();
            if (responseMatcher.isPresent()) {
                final Long correctLeftPhraseId = answer.getLeftPhrase().getPhraseId();
                final Long correctRightPhraseId = answer.getRightPhrase().getPhraseId();
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
    public QuestionMQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionMQOutDto dto = modelMapper.map(this, QuestionMQOutDto.class);
        dto.setLeftPhrases(new HashSet<>()).setLeftPhrases(new HashSet<>());
        dto.setHelpAvailable((getHelp().isPresent()) ? true : false);
        dto.setResources((getResources().isPresent()) ? getResources().get() : null);
        answers.forEach(a->dto.addLeftPhrase(a.toDto()));
        answers.forEach(a->dto.addRightPhrase(a.getRightPhrase()));
        return dto;
    }
}
