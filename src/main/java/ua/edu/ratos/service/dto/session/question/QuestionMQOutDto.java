package ua.edu.ratos.service.dto.session.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.PhraseDomain;
import ua.edu.ratos.service.dto.session.answer.AnswerMQOutDto;
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
     * (Randomized) set of rightPhraseDomains
     */
    private Set<PhraseDomain> rightPhraseDomains = new HashSet<>();

    public void addLeftPhrase(AnswerMQOutDto leftPhrase) {
        this.leftPhrases.add(leftPhrase);
    }

    public void addRightPhrase(PhraseDomain rightPhraseDomain) {
        this.rightPhraseDomains.add(rightPhraseDomain);
    }

    @Override
    public boolean isShufflingSupported() {
        return true;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        collectionShuffler.shuffle(leftPhrases);
        collectionShuffler.shuffle(rightPhraseDomains);
    }

}
