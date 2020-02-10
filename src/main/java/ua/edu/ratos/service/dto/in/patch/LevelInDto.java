package ua.edu.ratos.service.dto.in.patch;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class LevelInDto {

    @Range(min=1, max=3, message = "{dto.range.invalid}")
    private Byte value;
}
