package ua.edu.ratos.service.dto.out.grading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FreePointGradingOutDto {

    private Long freeId;

    private String name;

    private short minValue;

    private short passValue;

    private short maxValue;

    private StaffMinOutDto staff;
}
