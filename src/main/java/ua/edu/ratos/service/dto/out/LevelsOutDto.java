package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Getter
@Setter
@Accessors(chain = true)
public class LevelsOutDto {
    private String type;
    private int totalLevel1;
    private int totalLevel2;
    private int totalLevel3;
    private int total;
}
