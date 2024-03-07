package ua.edu.ratos.service.dto.session.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.session.answer.AnswerFBSQSessionOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
public class QuestionFBSQSessionOutDto extends QuestionSessionOutDto {

    private AnswerFBSQSessionOutDto answer;

    @Override
    public boolean isShufflingSupported() {
        return false;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        throw new UnsupportedOperationException("Shuffling is not supported for this type of questions");
    }
}
