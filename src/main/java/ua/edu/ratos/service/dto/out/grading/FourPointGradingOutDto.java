package ua.edu.ratos.service.dto.out.grading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FourPointGradingOutDto {

    private Long fourId;

    private String name;

    private byte threshold3;

    private byte threshold4;

    private byte threshold5;
}
