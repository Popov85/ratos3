package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SettingsOutDto {

    private Long setId;

    private String name;

    private int secondsPerQuestion;

    private short questionsPerSheet;

    private short daysKeepResultDetails;

    private float level2Coefficient;

    private float level3Coefficient;

    private boolean strictControlTimePerQuestion;

    private StaffMinOutDto staff;
}
