package ua.edu.ratos.service.session.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.session.domain.SettingsFB;
import ua.edu.ratos.service.session.domain.answer.AnswerFBMQ;
import ua.edu.ratos.service.session.dto.question.QuestionFBMQOutDto;
import ua.edu.ratos.service.session.domain.response.ResponseFillBlankMultiple;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class QuestionFBMQ extends Question {

    private Set<AnswerFBMQ> answers = new HashSet<>();

    public void addAnswer(AnswerFBMQ answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerFBMQ answer) {
        this.answers.remove(answer);
    }

    public double evaluate(ResponseFillBlankMultiple response) {
        int matchCounter = 0;
        final Set<ResponseFillBlankMultiple.Pair> pairs = response.getEnteredPhrases();
        for (AnswerFBMQ answer : answers) {
            Long answerId = answer.getAnswerId();
            // find this answerId in pairs, if not found - consider incorrect
            Optional<ResponseFillBlankMultiple.Pair> responseOnAnswerId = pairs
                    .stream()
                    .filter(p -> answerId.equals(p.getAnswerId()))
                    .findFirst();
            if (responseOnAnswerId.isPresent()) {
                String enteredPhrase = responseOnAnswerId.get().getEnteredPhrase();
                List<String> acceptedPhrases = answer.getAcceptedPhrases()
                        .stream()
                        .map(p -> p.getPhrase())
                        .collect(Collectors.toList());
                acceptedPhrases.add(answer.getPhrase());

                // Normal process
                if (acceptedPhrases.contains(enteredPhrase)) {
                    matchCounter++;
                    continue;
                }
                // Process case sensitivity
                SettingsFB settings = answer.getSettings();
                if (!settings.isCaseSensitive()) {
                    if (settings.checkCaseInsensitiveMatch(enteredPhrase, acceptedPhrases)) {
                        matchCounter++;
                        continue;
                    }
                }
                // Process typos
                if (settings.isTypoAllowed()) {
                    if (settings.checkSingleTypoMatch(enteredPhrase, acceptedPhrases)) matchCounter++;
                }
            }
        }
        // calculate correctCounter and all answers
        int totalAnswers = answers.size();
        // Check for partial response possibility
        if (!this.partialResponseAllowed && matchCounter<totalAnswers) return 0;

        return matchCounter*100d/totalAnswers;
    }

    @Override
    public QuestionFBMQOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBMQOutDto dto = modelMapper.map(this, QuestionFBMQOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelp().isPresent()) ? true : false);
        dto.setResources((getResources().isPresent()) ? getResources().get() : null);
        answers.forEach(a->dto.addAnswer(a.toDto()));
        return dto;
    }

}
