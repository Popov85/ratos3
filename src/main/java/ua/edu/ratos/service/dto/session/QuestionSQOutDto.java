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
public class QuestionSQOutDto extends QuestionOutDto {

    private Set<AnswerSQOutDto> answers = new HashSet<>();

    public void add(AnswerSQOutDto answer) {
        this.answers.add(answer);
    }
}
