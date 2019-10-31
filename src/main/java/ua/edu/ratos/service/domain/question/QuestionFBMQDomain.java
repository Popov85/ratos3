package ua.edu.ratos.service.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.service.domain.SettingsFBDomain;
import ua.edu.ratos.service.domain.answer.AnswerFBMQDomain;
import ua.edu.ratos.service.dto.out.answer.CorrectAnswerFBMQOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionFBMQSessionOutDto;
import ua.edu.ratos.service.domain.response.ResponseFBMQ;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true)
public class QuestionFBMQDomain extends QuestionDomain {

    private Set<AnswerFBMQDomain> answers = new HashSet<>();

    public void addAnswer(AnswerFBMQDomain answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(AnswerFBMQDomain answer) {
        this.answers.remove(answer);
    }

    public double evaluate(@NonNull final ResponseFBMQ response) {
        int matchCounter = 0;
        final Set<ResponseFBMQ.Pair> pairs = response.getEnteredPhrases();
        if (pairs==null || pairs.isEmpty()) return 0;
        for (AnswerFBMQDomain answer : answers) {
            Long answerId = answer.getAnswerId();
            // find this answerId in pairs, if not found - consider incorrect
            Optional<ResponseFBMQ.Pair> responseOnAnswerId = pairs
                    .stream()
                    .filter(p -> answerId.equals(p.getAnswerId()))
                    .findFirst();
            if (responseOnAnswerId.isPresent()) {
                String enteredPhrase = responseOnAnswerId.get().getEnteredPhrase();
                List<String> acceptedPhrases = answer.getAcceptedPhraseDomains()
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
                SettingsFBDomain settings = answer.getSettings();
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
        if (!this.partialResponseAllowed && matchCounter < totalAnswers) return 0;

        return matchCounter * 100d / totalAnswers;
    }

    @Override
    @JsonIgnore
    public CorrectAnswerFBMQOutDto getCorrectAnswer() {
        Set<CorrectAnswerFBMQOutDto.Pair> correctAnswers = this.answers
                .stream()
                .map(a -> new CorrectAnswerFBMQOutDto
                        .Pair(a.getAnswerId(), a.getAcceptedPhraseDomains()
                        .stream()
                        .map(p -> p.getPhrase())
                        .collect(Collectors.toSet())))
                .collect(Collectors.toSet());
        return new CorrectAnswerFBMQOutDto(correctAnswers);
    }

    @Override
    public QuestionFBMQSessionOutDto toDto() {
        ModelMapper modelMapper = new ModelMapper();
        QuestionFBMQSessionOutDto dto = modelMapper.map(this, QuestionFBMQSessionOutDto.class);
        dto.setAnswers(new HashSet<>());
        dto.setHelpAvailable((getHelpDomain().isPresent()) ? true : false);
        dto.setResourceDomains((getResourceDomains().isPresent()) ? getResourceDomains().get() : null);
        answers.forEach(a -> dto.addAnswer(a.toDto()));
        return dto;
    }

}
