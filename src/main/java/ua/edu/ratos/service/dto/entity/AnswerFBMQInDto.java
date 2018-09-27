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

    public interface NewAndUpdate{}
    public interface Include{}

    private Long answerId;

    @NotBlank(groups = {NewAndUpdate.class,  Include.class}, message = "{dto.string.required}")
    @Size(groups = {NewAndUpdate.class, Include.class}, min = 1, max = 200, message = "{dto.string.invalid}")
    private String phrase;

    @Range(groups = {NewAndUpdate.class, Include.class}, min=1, max=20, message = "{dto.range.invalid}")
    private byte occurrence;

    @Positive(groups = {NewAndUpdate.class, Include.class}, message = "{dto.fk.required}")
    private long setId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {Include.class}, message = "{dto.fk.nullable}")
    @Positive(groups = {NewAndUpdate.class}, message = "{dto.fk.required}")
    private Long questionId;

    @NotEmpty(groups = {NewAndUpdate.class, Include.class}, message = "{dto.collection.required}")
    @Size(groups = {NewAndUpdate.class, Include.class}, min = 1, max = 10, message = "{dto.collection.invalid}")
    private Set<Long> phrasesIds;
}
