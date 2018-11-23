package ua.edu.ratos.service.session.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Result {
    /**
     * Some key for edX integration score system purpose
     */
    private final String key;
    /**
     * Whether or not the given min threshold is passed, for edX purposes to notify if the test has been cracked
     */
    private final boolean passed;
    /**
     * Either [0-100], fractions are allowed
     * E.g. 75.4
     */
    private final double percent;
    /**
     * Any number according to the selected grading scale, e.g. four-point [2, 3, 4, 5]
     * E.g. 4
     * Or e.g. two-point {0, 1}
     * E.g 1
     */
    private final double grade;

    private final List<ResultPerTheme> themeResults;
}
