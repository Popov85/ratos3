package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ThemeExtOutDto extends ThemeOutDto {

    // total question of all types
    private int total;

    // Total questions by questionType
    private Set<TypeMinOutDto> totalByType = new HashSet<>();
}
