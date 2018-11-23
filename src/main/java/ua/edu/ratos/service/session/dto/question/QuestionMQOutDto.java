package ua.edu.ratos.service.session.dto.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.domain.Phrase;
import ua.edu.ratos.service.session.dto.answer.AnswerMQOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionMQOutDto extends QuestionOutDto {

    /**
     * (Randomized) set of AnswerMQOutDto: {answerId; leftPhraseId}
     */
    private Set<AnswerMQOutDto> leftPhrases = new HashSet<>();

    /**
     * (Randomized) set of rightPhrases
     */
    private Set<Phrase> rightPhrases = new HashSet<>();

    public void addLeftPhrase(AnswerMQOutDto leftPhrase) {
        this.leftPhrases.add(leftPhrase);
    }

    public void addRightPhrase(Phrase rightPhrase) {
        this.rightPhrases.add(rightPhrase);
    }

    @Override
    public boolean isShufflingSupported() {
        return true;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        collectionShuffler.shuffle(leftPhrases);
        collectionShuffler.shuffle(rightPhrases);
    }

}
