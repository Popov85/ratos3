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
public class ThemeExtendedOutDto extends ThemeOutDto {

    private Set<TypeMinOutDto> typeDetails = new HashSet<>();

    private int total;
}
