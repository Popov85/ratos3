package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Free-word answer, multiple blanks to fill acceptable
 * @author Andrey P.
 */
@Setter
@Getter
@ToString
public class AnswerFillBlankMultiple extends AnswerFillBlankSingle implements Answer {
    private String phrase;
    private byte occurrence = 1;

    @Override
    public boolean isValid() {
        if (!super.isValid()) return false;
        if (this.phrase == null || this.phrase.isEmpty()) return false;
        if (this.occurrence <0 || this.occurrence>100) return false;
        return true;
    }
}
