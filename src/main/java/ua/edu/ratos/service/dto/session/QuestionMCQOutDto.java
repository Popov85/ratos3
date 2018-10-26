package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionMCQOutDto extends QuestionOutDto {

    private boolean isSingle;

    private Set<AnswerMCQOutDto> answers = new HashSet<>();

    public void add(AnswerMCQOutDto answer) {
        this.answers.add(answer);
    }
}
