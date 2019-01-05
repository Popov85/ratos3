package ua.edu.ratos.service.dto.session.question;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.session.answer.AnswerFBMQOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionFBMQOutDto extends QuestionOutDto {

    private Set<AnswerFBMQOutDto> answers = new HashSet<>();

    public void addAnswer(AnswerFBMQOutDto answer) {
        this.answers.add(answer);
    }

    @Override
    public boolean isShufflingSupported() {
        return false;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        throw new UnsupportedOperationException("Shuffling is not supported for this type of questions");
    }
}
