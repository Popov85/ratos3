package ua.edu.ratos.domain.entity.question;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import ua.edu.ratos.domain.entity.answer.AnswerMatcher;
import ua.edu.ratos.service.dto.response.ResponseMatcher;
import ua.edu.ratos.service.dto.session.QuestionMQOutDto;
import ua.edu.ratos.service.dto.session.QuestionOutDto;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Match.
 * Matching questions consist of two columns, typically one column is on the left and one column is on the right.
 * We will refer to the left side as 'Clues' and the right side as 'Matches'.
 * The objective is to pair the clues on the left side with their matches on the right.
 * These can be created with using help on both sides or a mix of help with media,
 * such as images, audio or video.
 *
 * @see <a href="https://www.classmarker.com/learn/question-types/matching-questions/">Match</a>
 * @author Andrey P.
 */

@Slf4j
@Setter
@Getter
@Entity
@Cacheable
@DiscriminatorValue(value = "4")
@DynamicUpdate
public class QuestionMatcher extends Question {

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<AnswerMatcher> answers = new ArrayList<>();

    public void addAnswer(AnswerMatcher answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    /**
     * Not strict match, each match contributes to the resulting score!
     * @param response
     * @return
     */
    public int evaluate(ResponseMatcher response) {
        final Set<ResponseMatcher.Triple> responses = response.getMatchedPhrases();
        // Traverse through all the answers of this question
        int matchCounter = 0;
        int totalMatches = answers.size();
        for (AnswerMatcher answer : answers) {
            final Long nextAnswerId = answer.getAnswerId();
            final Optional<ResponseMatcher.Triple> responseMatcher = responses
                    .stream()
                    .filter(r -> r.getAnswerId() == nextAnswerId)
                    .findFirst();
            if (responseMatcher.isPresent()) {
                final Long correctLeftPhraseId = answer.getLeftPhrase().getPhraseId();
                final Long correctRightPhraseId = answer.getRightPhrase().getPhraseId();
                final long responseLeftPhraseId = responseMatcher.get().getLeftPhraseId();
                final long responseRightPhraseId = responseMatcher.get().getRightPhraseId();
                if (correctLeftPhraseId == responseLeftPhraseId && correctRightPhraseId == responseRightPhraseId)
                    matchCounter++;
            }// else consider this matcher as incorrect, go to the next answer (matcher)
        }
        // calculate the final score based on totalMatcher value and matchCounter
        return (int) (matchCounter*100d/totalMatches);
    }

    @Override
    public QuestionMQOutDto toDto(boolean mixable) {
        ModelMapper modelMapper = new ModelMapper();
        final QuestionOutDto questionOutDto = super.toDto(mixable);
        QuestionMQOutDto dto = modelMapper
                .map(questionOutDto, QuestionMQOutDto.class);
        this.answers.forEach(a-> dto.addLeftPhrase(a.toDto()));
        this.answers.forEach(a-> dto.addRightPhrase(a.getRightPhrase()));
        if (mixable) {
            dto.setLeftPhrases(collectionShuffler.shuffle(dto.getLeftPhrases()).stream().collect(Collectors.toSet()));
            dto.setRightPhrases(collectionShuffler.shuffle(dto.getRightPhrases()).stream().collect(Collectors.toSet()));
        }
        return dto;
    }

    @Override
    public String toString() {
        return "QuestionMatcher{" +
                ", questionId=" + questionId +
                ", question='" + question + '\'' +
                ", level=" + level +
                ", deleted=" + deleted +
                ", type=" + type.getAbbreviation() +
                ", lang=" + lang.getAbbreviation() +
                '}';
    }
}
