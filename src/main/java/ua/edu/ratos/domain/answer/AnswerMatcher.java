package ua.edu.ratos.domain.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AnswerMatcher {
    private long answerId;
    private AnswerMatcherLeft leftMatcher;
    private AnswerMatcherRight rightMatcher;

    public boolean isValid() {
        if (this.leftMatcher == null || this.rightMatcher==null) return false;
        if (this.leftMatcher.getLeftPhrase().isEmpty()) return false;
        if (this.rightMatcher.getRightPhrase().isEmpty()) return false;
        return true;
    }
}
