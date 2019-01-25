package ua.edu.ratos.service.dto.out.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.ResourceOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AnswerMCQOutDto {

    private Long answerId;

    private String answer;

    private short percent;

    private boolean isRequired;

    private ResourceOutDto resource;

}
