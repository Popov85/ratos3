package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    public interface New{}
    public interface Update {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class}, message = "Invalid answerId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "Invalid answerId, {dto.pk.required}")
    private Long answerId;

    @Positive(groups = {New.class, Update.class}, message = "Invalid setId, {dto.fk.required}")
    private long setId;

    @NotEmpty(groups = {New.class, Update.class}, message = "Invalid phrasesIds, {dto.collection.required}")
    @Size(groups = {New.class, Update.class}, min = 1, max = 20, message = "Invalid phrasesIds, {dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
