package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.QuestionType;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QuestionOutDto {

    protected Long questionId;

    protected String question;

    protected byte level;

    protected Theme theme;

    protected QuestionType type;

    protected Language lang;

    protected Set<Help> help = new HashSet<>();

    protected Set<Resource> resources = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionOutDto that = (QuestionOutDto) o;
        return Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
