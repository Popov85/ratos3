package ua.edu.ratos.service.dto.session.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.domain.ResourceDomain;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerMCQSessionOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long answerId;

    private String answer;

    // Nullable
    private ResourceDomain resourceDomain;

}
