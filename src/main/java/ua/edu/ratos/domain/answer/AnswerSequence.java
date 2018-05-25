package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.Resource;

@Setter
@Getter
@ToString
public class AnswerSequence {
    private long answerId;
    private String phrase;
    private short order;
    private Resource resource;

    public boolean isValid() {
        if (this.phrase==null || this.phrase.isEmpty()) return false;
        if (order<0 || order>100) return false;
        return true;
    }
}
