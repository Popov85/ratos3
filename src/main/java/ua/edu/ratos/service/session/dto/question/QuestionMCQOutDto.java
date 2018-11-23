package ua.edu.ratos.service.session.dto.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.session.dto.answer.AnswerMCQOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionMCQOutDto extends QuestionOutDto {

    private boolean isSingle;

    private Set<AnswerMCQOutDto> answers = new HashSet<>();

    public void addAnswer(AnswerMCQOutDto answer) {
        this.answers.add(answer);
    }

    @Override
    public boolean isShufflingSupported() {
        return true;
    }

    @Override
    public void shuffle(CollectionShuffler collectionShuffler) {
        collectionShuffler.shuffle(answers);
    }
}
