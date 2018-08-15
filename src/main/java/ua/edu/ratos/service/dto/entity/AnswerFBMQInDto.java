package ua.edu.ratos.service.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    public interface Include{}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {New.class, Include.class}, message = "Non-null answerId, {dto.pk.nullable}")
    @NotNull(groups = {Update.class}, message = "{dto.pk.required}")
    private Long answerId;

    @Size(groups = {New.class, Update.class, Include.class}, min = 1, max = 200, message = "Invalid phrase, {dto.string.invalid}")
    private String phrase;

    @Range(groups = {New.class, Update.class, Include.class}, min=1, max=20, message = "Invalid occurrence, {dto.range.invalid}")
    private byte occurrence;

    @Positive(groups = {New.class, Update.class, Include.class}, message = "Invalid setId, {dto.fk.required}")
    private long setId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "Non-null questionId, {dto.fk.nullable}")
    @Positive(groups = {New.class, Update.class}, message = "Invalid questionId, {dto.fk.required}")
    private Long questionId;

    @NotEmpty(groups = {New.class, Update.class, Include.class}, message = "Invalid phrasesIds, {dto.collection.required}")
    @Size(groups = {New.class, Update.class, Include.class}, min = 1, max = 10, message = "Invalid phrasesIds, {dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
