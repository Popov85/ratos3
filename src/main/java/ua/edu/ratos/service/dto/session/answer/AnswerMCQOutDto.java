package ua.edu.ratos.service.dto.session.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.ResourceDomain;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerMCQOutDto {

    private Long answerId;

    private String answer;

    // Nullable
    private ResourceDomain resourceDomain;

}
