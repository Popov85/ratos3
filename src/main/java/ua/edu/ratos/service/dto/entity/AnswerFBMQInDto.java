package ua.edu.ratos.service.dto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.*;
import java.util.Set;


@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
/**
 * @link http://www.bytestree.com/spring/spring-boot-restful-web-services-validation-hibernate-validator/
 * @link https://habr.com/post/343960/
 */
public class AnswerFBMQInDto {

    public interface New{}
    public interface Update{}

    @Null(groups = {New.class}, message = "{dto.pk.invalid}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long answerId;

    @NotEmpty(message = "{dto.phrase.required}")
    @Size(min = 1, max = 100, message = "{dto.phrase.invalid}")
    private String phrase;

    @Range(min=1, max=10, message = "{dto.occurrence.invalid}")
    private byte occurrence;

    @Positive(message = "Invalid setId, {dto.fk.invalid}")
    private long setId;

    @Positive(message = "Invalid questionId, {dto.fk.invalid}")
    private long questionId;

    @NotNull(message = "Invalid phrasesIds, {dto.collection.required}")
    @Size(min = 1, max = 10, message = "Invalid phrasesIds, {dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
