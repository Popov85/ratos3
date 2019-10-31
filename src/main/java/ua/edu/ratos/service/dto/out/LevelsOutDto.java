package ua.edu.ratos.service.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LevelsOutDto {
    private String type;
    private int totalLevel1;
    private int totalLevel2;
    private int totalLevel3;
    private int total;
}
