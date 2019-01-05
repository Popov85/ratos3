package ua.edu.ratos.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResultPerTheme {

    private final Long themeId;

    private final String theme;

    private final int quantity;

    private final boolean passed;

    private final double percent;

    private final double grade;

}
