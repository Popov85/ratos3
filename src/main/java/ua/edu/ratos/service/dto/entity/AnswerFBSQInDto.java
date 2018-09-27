package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;
import java.util.Set;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFBSQInDto {

    private Long answerId;

    @Positive(message = "{dto.fk.required}")
    private long setId;

    @NotEmpty(message = "{dto.collection.required}")
    @Size(min = 1, max = 20, message = "{dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
