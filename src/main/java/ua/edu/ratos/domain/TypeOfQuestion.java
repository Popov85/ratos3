package ua.edu.ratos.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The questions types of general purpose:
 * {@link  ua.edu.ratos.domain.answer.AnswerTypeA TypeA}
 * {@link  ua.edu.ratos.domain.answer.AnswerTypeB TypeB}
 * {@link  ua.edu.ratos.domain.answer.AnswerTypeC TypeC}
 * {@link  ua.edu.ratos.domain.answer.AnswerTypeD TypeD}
 * {@link  ua.edu.ratos.domain.answer.AnswerTypeE TypeE}
 *
 * There may be more domain-specific types in the following releases!
 *
 * @author Andrey P.
 * @see <a href="https://www.classmarker.com/learn/question-types/">Types</a>
 */
@Setter
@Getter
@ToString
public class TypeOfQuestion {
    private int typeId;
    private String type;
    private String description;

    public TypeOfQuestion() {}
    public TypeOfQuestion(int typeId) {
        this.typeId = typeId;
    }

}
