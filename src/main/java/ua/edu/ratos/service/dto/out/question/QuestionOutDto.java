package ua.edu.ratos.service.dto.out.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public abstract class QuestionOutDto {

    protected Long questionId;

    protected String question;

    protected byte level;

    protected boolean required;

    protected QuestionTypeOutDto type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected HelpMinOutDto help;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected ResourceOutDto resource;

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
