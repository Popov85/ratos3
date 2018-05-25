package ua.edu.ratos.domain.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.domain.model.Help;
import ua.edu.ratos.domain.model.Resource;
import ua.edu.ratos.domain.model.Theme;
import java.util.Optional;

@Getter
@Setter
@ToString
public abstract class Question {
    protected long questionId;
    protected String question;
    protected byte level;
    protected Theme theme;
    protected Help help;
    protected Resource resource;

    public Optional<Help> getHelp() {
        return Optional.ofNullable(help);
    }

    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    public boolean isValid() {
        if (this.question==null) return false;
        if (this.question.isEmpty()) return false;
        if (this.level <1 || this.level>3) return false;
        //if (this.theme==null) return false;
        return true;
    }
}
