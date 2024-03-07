package ua.edu.ratos.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResultPerTheme {

    private ThemeDomain theme;

    // How many questions answered on this theme
    private final int quantity;

    private final double percent;

}
