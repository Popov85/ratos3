package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.Resource;

@Setter
@Getter
@ToString
public class AnswerMultipleChoice {
    private long answerId;
    private String answer;
    private short percent;
    private boolean isRequired;
    private Resource resource;

    public boolean isValid() {
        if (answer == null) return false;
        if (answer.isEmpty())return false;
        if (percent < 0 || percent > 100) return false;
        if (percent==0 && isRequired) isRequired = false;
        return true;
    }
}
