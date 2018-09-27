package ua.edu.ratos.service.dto.entity;

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

    @NotBlank(groups = {NewAndUpdate.class, Include.class}, message = "{dto.string.required}")
    @Size(groups = {NewAndUpdate.class, Include.class}, min = 1, max = 100, message = "{dto.string.invalid}")
    private String leftPhrase;

    @NotBlank(groups = {NewAndUpdate.class, Include.class}, message = "{dto.string.required}")
    @Size(groups = {NewAndUpdate.class, Include.class}, min = 1, max = 500, message = "{dto.string.invalid}")
    private String rightPhrase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.optional}")
    private long rightPhraseResourceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "{dto.fk.nullable}")
    @Positive(groups = {NewAndUpdate.class}, message = "{dto.fk.invalid}")
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerMQInDto that = (AnswerMQInDto) o;
        return Objects.equals(leftPhrase, that.leftPhrase) &&
                Objects.equals(rightPhrase, that.rightPhrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPhrase, rightPhrase);
    }
}
