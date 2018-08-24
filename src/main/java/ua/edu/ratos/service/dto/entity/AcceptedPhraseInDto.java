package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AcceptedPhraseInDto {

    public interface New{}
    public interface Update{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "{dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long phraseId;

    @NotBlank(groups = {New.class, Update.class}, message = "Invalid phrase, {dto.string.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 100, message = "Invalid phrase, {dto.string.invalid}")
    private String phrase;

    @Positive(groups = {New.class, Update.class}, message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
}
