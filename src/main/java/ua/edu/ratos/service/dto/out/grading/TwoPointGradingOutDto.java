package ua.edu.ratos.service.dto.out.grading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TwoPointGradingOutDto {

    private Long twoId;

    private String name;

    private byte threshold;
}
