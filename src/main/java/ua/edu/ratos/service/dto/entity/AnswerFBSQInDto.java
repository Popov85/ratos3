package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFBSQInDto {

    public interface UpdateAndInclude{}

    @NotNull(groups = {AnswerFBSQInDto.UpdateAndInclude.class}, message = "{dto.pk.required}")
    private Long answerId;

    @Positive(message = "Invalid setId, {dto.fk.invalid}")
    private long setId;

    @NotEmpty(message = "Invalid phrasesIds, {dto.collection.required}")
    @Size(min = 1, max = 10, message = "Invalid phrasesIds, {dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
