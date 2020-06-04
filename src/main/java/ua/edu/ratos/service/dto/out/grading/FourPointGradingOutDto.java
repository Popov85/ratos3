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
public class FourPointGradingOutDto {

    private Long fourId;

    private String name;

    private byte threshold3;

    private byte threshold4;

    private byte threshold5;

    private boolean isDefault;

    private StaffMinOutDto staff;

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return isDefault;
    }
}
