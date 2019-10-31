package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.service.dto.out.ThemeOutDto;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResultPerThemeOutDto {

    private ThemeOutDto theme;

    // How many questions answered on this theme
    private int quantity;

    private double percent;
}
