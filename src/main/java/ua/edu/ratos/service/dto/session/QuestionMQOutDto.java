package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.domain.entity.Phrase;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionMQOutDto extends QuestionOutDto {

    /**
     * Randomized set of AnswerMQOutDto: {answerId; leftPhraseId}
     */
    private Set<AnswerMQOutDto> leftPhrases = new HashSet<>();

    /**
     * Randomized set of rightPhrases
     */
    private Set<Phrase> rightPhrases = new HashSet<>();

    public void addLeftPhrase(AnswerMQOutDto leftPhrase) {
        this.leftPhrases.add(leftPhrase);
    }

    public void addRightPhrase(Phrase rightPhrase) {
        this.rightPhrases.add(rightPhrase);
    }

}
