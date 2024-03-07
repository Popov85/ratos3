package ua.edu.ratos.service.dto.session.question;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;
import ua.edu.ratos.service.dto.out.ThemeMinOutDto;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
// Used for a user's starred questions table
public class QuestionSessionMinOutDto  {

    private Long questionId;

    private String question;

    private byte level;

    private long type;

    private String lang;

    private ThemeMinOutDto theme;

    private boolean helpAvailable;

    private Set<ResourceMinOutDto> resources = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionSessionMinOutDto that = (QuestionSessionMinOutDto) o;
        return Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
