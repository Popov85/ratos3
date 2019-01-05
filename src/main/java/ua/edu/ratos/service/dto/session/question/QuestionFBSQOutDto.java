package ua.edu.ratos.service.dto.session.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.session.answer.AnswerFBSQOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionFBSQOutDto extends QuestionOutDto{

    private AnswerFBSQOutDto answer;

    @Override
    public boolean isShufflingSupported() {
        return false;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        throw new UnsupportedOperationException("Shuffling is not supported for this type of questions");
    }
}
