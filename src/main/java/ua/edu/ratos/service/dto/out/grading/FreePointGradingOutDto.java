package ua.edu.ratos.service.dto.out.grading;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private boolean isDefault;

    private StaffMinOutDto staff;

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return isDefault;
    }
}
