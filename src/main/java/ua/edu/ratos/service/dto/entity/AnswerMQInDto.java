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

    public interface New{}
    public interface Update{}
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class, Include.class}, message = "Non-null answerId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "Invalid answerId, {dto.pk.required}")
    private Long answerId;

    @NotBlank(groups = {New.class, Update.class, Include.class}, message = "Invalid leftPhrase, {dto.string.required}")
    @Size(groups = {New.class, Update.class, Include.class}, min = 1, max = 100, message = "Invalid leftPhrase, {dto.string.invalid}")
    private String leftPhrase;

    @NotBlank(groups = {New.class, Update.class, Include.class}, message = "Invalid rightPhrase, {dto.string.required}")
    @Size(groups = {New.class, Update.class, Include.class}, min = 1, max = 500, message = "Invalid rightPhrase, {dto.string.invalid}")
    private String rightPhrase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(groups = {New.class, Update.class, Include.class}, message = "Invalid rightPhraseResourceId, {dto.fk.optional}")
    private long rightPhraseResourceId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "Non-null questionId, {dto.fk.nullable}")
    @Positive(groups = {New.class, Update.class}, message = "Invalid questionId, {dto.fk.invalid}")
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
