package ua.edu.ratos.service.dto.session.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.session.answer.AnswerSQOutDto;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionSQOutDto extends QuestionOutDto {

    private Set<AnswerSQOutDto> answers = new HashSet<>();

    public void addAnswer(AnswerSQOutDto answer) {
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
