package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.Resource;

@Setter
@Getter
@ToString
public class AnswerMultipleChoice implements Answer {
    private long answerId;
    private String answer;
    private short percent;
    private boolean isRequired;
    private Resource resource;

    @Override
    public boolean isValid() {
        if (answer == null) return false;
        if (answer.isEmpty())return false;
        if (percent < 0 || percent > 100) return false;
        if (percent==0 && isRequired) isRequired = false;
        return true;
    }
}
