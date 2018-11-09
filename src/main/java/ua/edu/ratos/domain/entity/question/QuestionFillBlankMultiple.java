package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.domain.entity.SettingsAnswerFillBlank;
import ua.edu.ratos.domain.entity.answer.AnswerFillBlankMultiple;
import ua.edu.ratos.service.dto.response.ResponseFillBlankMultiple;
import ua.edu.ratos.service.dto.session.QuestionFBMQOutDto;
import ua.edu.ratos.service.dto.session.QuestionOutDto;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "3")
@DynamicUpdate
public class QuestionFillBlankMultiple extends Question{

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AnswerFillBlankMultiple> answers = new HashSet<>();

    public void addAnswer(AnswerFillBlankMultiple answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(AnswerFillBlankMultiple answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
    }

    public double evaluate(ResponseFillBlankMultiple response) {
        int matchCounter = 0;
        final Set<ResponseFillBlankMultiple.Pair> pairs = response.getEnteredPhrases();
        for (AnswerFillBlankMultiple answer : answers) {
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
                SettingsAnswerFillBlank settings = answer.getSettings();
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
    public QuestionFBMQOutDto toDto(boolean mixable) {
        ModelMapper modelMapper = new ModelMapper();
        final QuestionOutDto questionOutDto = super.toDto(mixable);
        QuestionFBMQOutDto dto = modelMapper
                .map(questionOutDto, QuestionFBMQOutDto.class);
        this.answers.forEach(a-> dto.add(a.toDto()));
        // Does not support any kind of shuffling
        return dto;
    }

    @Override
    public String toString() {
        return "QuestionFillBlankMultiple{" +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                ", type=" + (type!=null ? type.getAbbreviation(): null) +
                ", lang=" + (lang!=null ? lang.getAbbreviation() : null) +
                '}';
    }
}
