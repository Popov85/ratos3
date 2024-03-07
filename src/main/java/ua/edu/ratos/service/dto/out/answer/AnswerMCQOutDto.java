package ua.edu.ratos.service.dto.out.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.ResourceMinOutDto;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "answerId")
@Accessors(chain = true)
public class AnswerMCQOutDto {

    private Long answerId;

    private String answer;

    private short percent;

    @JsonProperty("required")
    private boolean isRequired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResourceMinOutDto resource;
}
