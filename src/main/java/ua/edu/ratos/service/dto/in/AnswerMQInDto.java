package ua.edu.ratos.service.dto.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;
import java.util.Objects;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerMQInDto {

    public interface NewAndUpdate{}
    public interface Include{}

    private Long answerId;

    @Positive(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.invalid}")
    private Long leftPhraseId;

    @Positive(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.invalid}")
    private Long rightPhraseId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "{dto.fk.nullable}")
    @Positive(groups = {NewAndUpdate.class}, message = "{dto.fk.invalid}")
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMQInDto that = (AnswerMQInDto) o;
        return Objects.equals(leftPhraseId, that.leftPhraseId) &&
                Objects.equals(rightPhraseId, that.rightPhraseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPhraseId, rightPhraseId);
    }
}
